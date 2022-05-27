package com.app.toalarm.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.toalarm.domain.models.params.TaskParams
import com.app.toalarm.domain.usecases.category.FetchAllCategoriesUseCase
import com.app.toalarm.domain.usecases.category.FilterTasksByStateUseCase
import com.app.toalarm.domain.usecases.task.FetchTasksByCategoryIdAndDateUseCase
import com.app.toalarm.domain.utils.ResultOf
import com.app.toalarm.mappers.toDomain
import com.app.toalarm.mappers.toUI
import com.app.toalarm.models.CategoryUI
import com.app.toalarm.models.TaskListDetails
import com.app.toalarm.models.TaskUI
import com.app.toalarm.utils.Resource
import com.app.toalarm.utils.enums.TaskState
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate

/**
 * @ClassName: MainViewModel
 * @Description: class description
 * @Author: Arthur Galoyan
 * @Date: 4/2/2022 10:55 AM
 */
class HomeViewModel(
    private val fetchAllCategoriesUseCase: FetchAllCategoriesUseCase,
    private val fetchTasksByCategoryIdAndDateUseCase: FetchTasksByCategoryIdAndDateUseCase,
    private val filterByStateUseCase: FilterTasksByStateUseCase
) : ViewModel() {

    private val mCategoriesFlow = MutableStateFlow<Resource<List<CategoryUI>>>(Resource.loading())
    val categoriesFlow = mCategoriesFlow.asStateFlow()

    private val mTasksFlow = MutableSharedFlow<Resource<List<TaskUI>>>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val tasksFlow = mTasksFlow.asSharedFlow()

    private val mTaskListDetailsFlow = MutableStateFlow(TaskListDetails(1, LocalDate.now()))

    var isRestored = false

    init {
        fetchCategories()
        viewModelScope.launch {
            mTaskListDetailsFlow.collectLatest { details ->
                fetchTasksByCategoryIdAndDate(details)
            }
        }
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            when (val result = fetchAllCategoriesUseCase()) {
                is ResultOf.Success -> {
                    var uiCategories: List<CategoryUI>
                    result.data.collect { categories ->
                        uiCategories = categories.map { it.toUI() }
                        mCategoriesFlow.value = Resource.success(uiCategories)
                    }
                }
                is ResultOf.Error ->
                    mCategoriesFlow.value = Resource.error(result.exception, result.message)

            }
        }
    }

    private fun fetchTasksByCategoryIdAndDate(details: TaskListDetails) {
        viewModelScope.launch {
            val taskParams = TaskParams(categoryId = details.categoryId, taskDate = details.date)
            var uiTasks: List<TaskUI>
            fetchTasksByCategoryIdAndDateUseCase(taskParams).collectLatest { tasks ->
                uiTasks = tasks.map { it.toUI() }
                mTasksFlow.tryEmit(Resource.success(uiTasks))
            }
            /*when (val result = fetchTasksByCategoryIdAndDateUseCase(taskParams)) {
                is ResultOf.Success -> {
                    var uiTasks: List<TaskUI>
                    result.data.collectLatest { tasks ->
                        Log.d("FETCH_TASKS", tasks.toString())
                        uiTasks = tasks.map { it.toUI() }
                        mTasksFlow.value = Resource.success(uiTasks)
                    }
                }
                is ResultOf.Error -> mTasksFlow.value = Resource.error(result.exception)
            }*/
        }
    }

    fun filterByState(tasks: List<TaskUI>, state: TaskState) {
        viewModelScope.launch {
            val data = filterByStateUseCase(
                tasks = tasks.map { it.toDomain() },
                isActive = state == TaskState.ACTIVE
            ).map { it.toUI() }
            mTasksFlow.tryEmit(Resource.success(data))
        }
    }

    fun selectCategory(categoryId: Long) {
        mTaskListDetailsFlow.value = mTaskListDetailsFlow.value.copy(categoryId = categoryId)
    }

    fun selectDate(date: LocalDate) {
        mTaskListDetailsFlow.value = mTaskListDetailsFlow.value.copy(date = date)
    }
}