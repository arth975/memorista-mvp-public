package com.app.toalarm.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.toalarm.domain.usecases.task.FetchTasksByCategoryIdAndDateUseCase
import com.app.toalarm.domain.usecases.category.FilterTasksByDateUseCase
import com.app.toalarm.domain.usecases.category.FilterTasksByStateUseCase
import com.app.toalarm.domain.utils.ResultOf
import com.app.toalarm.mappers.toDomain
import com.app.toalarm.mappers.toUI
import com.app.toalarm.models.TaskUI
import com.app.toalarm.utils.Resource
import com.app.toalarm.utils.enums.TaskState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate

class TaskListViewModel(
    private val fetchTasksByCategoryIdAndDateUseCase: FetchTasksByCategoryIdAndDateUseCase,
    private val filterByDateUseCase: FilterTasksByDateUseCase,
    private val filterByStateUseCase: FilterTasksByStateUseCase
) : ViewModel() {

    private val mTaskFlow = MutableStateFlow<Resource<List<TaskUI>>>(Resource.loading())
    val taskFlow = mTaskFlow.asStateFlow()

    fun fetchCategoriesWithTasksByIdAndDate(id: Long, date: LocalDate) {
        /*viewModelScope.launch {
            when (val result = fetchTasksByCategoryIdAndDateUseCase(id, date)) {
                is ResultOf.Success -> {
                    result.data.collectLatest { taskList ->
                        Resource.success(taskList)
                    }
                }
                is ResultOf.Error -> {
                    mTaskFlow.value = Resource.error(result.exception)
                }
            }
        }*/
    }

    fun filterByState(tasks: List<TaskUI>, state: TaskState) {
        viewModelScope.launch {
            val data = filterByStateUseCase(
                tasks.map { it.toDomain() },
                isActive = state == TaskState.ACTIVE
            ).map { it.toUI() }
            mTaskFlow.value = Resource.success(data)
        }
    }
}