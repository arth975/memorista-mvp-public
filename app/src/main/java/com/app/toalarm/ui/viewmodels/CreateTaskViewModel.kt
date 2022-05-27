package com.app.toalarm.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.toalarm.domain.models.Task
import com.app.toalarm.domain.models.params.NewTaskParams
import com.app.toalarm.domain.usecases.category.FetchAllCategoriesUseCase
import com.app.toalarm.domain.usecases.task.CreateTaskUseCase
import com.app.toalarm.domain.usecases.task.ValidateTaskInputUseCase
import com.app.toalarm.domain.utils.ResultOf
import com.app.toalarm.mappers.toUI
import com.app.toalarm.models.CategoryUI
import com.app.toalarm.models.Validation
import com.app.toalarm.utils.Resource
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class CreateTaskViewModel(
    private val fetchAllCategoriesUseCase: FetchAllCategoriesUseCase,
    private val validateTaskInputUseCase: ValidateTaskInputUseCase,
    private val createTaskUseCase: CreateTaskUseCase
) : ViewModel() {

    private val mCategoriesFlow = MutableStateFlow<Resource<List<CategoryUI>>>(Resource.loading())
    val categoriesFlow = mCategoriesFlow.asStateFlow()

    private val mValidationSuccessFlow =
        MutableSharedFlow<Validation>(
            replay = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
    val validationSuccessFlow = mValidationSuccessFlow.asSharedFlow()

    init {
        fetchCategories()
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
                is ResultOf.Error -> {
                    mCategoriesFlow.value = Resource.error(result.exception, result.message)
                }
            }
        }
    }

    fun createTask(
        title: String?,
        notes: String?,
        categoryId: Long?,
        taskDateTime: LocalDateTime?,
        alarmDateTime: LocalDateTime? = null
    ) {
        viewModelScope.launch {
            val isValid = validateTaskInputUseCase(
                NewTaskParams(
                    title = title,
                    notes = notes,
                    categoryId = categoryId,
                    taskDateTime = taskDateTime,
                    alarmDateTime = alarmDateTime
                )
            )
            if (isValid)
                createTaskUseCase(
                    Task(
                        title = title!!,
                        note = notes!!,
                        taskDateTime = taskDateTime!!,
                        categoryId = categoryId!!,
                        isActive = true,
                        alarmDateTime = alarmDateTime,
                        id = 0
                    )
                )
            mValidationSuccessFlow.tryEmit(Validation(isValid = isValid))
        }
    }
}