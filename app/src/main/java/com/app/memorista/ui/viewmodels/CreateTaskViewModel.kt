package com.app.memorista.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.app.memorista.domain.models.params.NewTaskParams
import com.app.memorista.domain.usecases.list.GetAllListsUseCase
import com.app.memorista.domain.usecases.task.CreateTaskUseCase
import com.app.memorista.domain.usecases.task.ValidateTaskInputUseCase
import com.app.memorista.models.TaskListUI
import com.app.memorista.models.Validation
import com.app.memorista.utils.models.TaskDetails
import com.app.memorista.utils.models.TaskDetailsEvent
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class CreateTaskViewModel(
    getAllListsUseCase: GetAllListsUseCase,
    private val validateTaskInputUseCase: ValidateTaskInputUseCase,
    private val createTaskUseCase: CreateTaskUseCase
) : CommonViewModel(getAllListsUseCase) {

    private val mValidationSuccessFlow = MutableSharedFlow<Validation>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val validationSuccessFlow = mValidationSuccessFlow.asSharedFlow()

    private val mTaskDetailsFlow = MutableStateFlow(TaskDetails())
    val taskDetailsFlow = mTaskDetailsFlow.asStateFlow()

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
            mValidationSuccessFlow.tryEmit(Validation(isValid = isValid))
        }
    }

    fun selectDate(date: LocalDate) {
        mTaskDetailsFlow.value = mTaskDetailsFlow.value.copy(
            date = date,
            event = TaskDetailsEvent.DateChanged
        )
    }

    fun selectTime(time: LocalTime) {
        mTaskDetailsFlow.value = mTaskDetailsFlow.value.copy(
            time = time,
            event = TaskDetailsEvent.TimeChanged
        )
    }

    fun setActivity(isActive: Boolean) {
        mTaskDetailsFlow.value = mTaskDetailsFlow.value.copy(
            isActive = isActive,
            event = TaskDetailsEvent.ActivityChanged
        )
    }

    fun selectList(list: TaskListUI) {
        mTaskDetailsFlow.value = mTaskDetailsFlow.value.copy(
            list = list,
            event = TaskDetailsEvent.ListChanged
        )
    }
}