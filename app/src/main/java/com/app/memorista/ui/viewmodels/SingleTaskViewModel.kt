package com.app.memorista.ui.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.app.memorista.domain.models.Task
import com.app.memorista.domain.models.params.NewTaskParams
import com.app.memorista.domain.usecases.list.GetAllListsUseCase
import com.app.memorista.domain.usecases.task.CreateTaskUseCase
import com.app.memorista.domain.usecases.task.UpdateTaskUseCase
import com.app.memorista.domain.usecases.task.ValidateTaskInputUseCase
import com.app.memorista.mappers.toDomain
import com.app.memorista.models.PriorityUI
import com.app.memorista.models.TaskListUI
import com.app.memorista.models.TaskUI
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
import java.time.LocalTime

class SingleTaskViewModel(
    private val updateTask: UpdateTaskUseCase,
    private val createTask: CreateTaskUseCase,
    private val validateTaskInput: ValidateTaskInputUseCase,
    getAllLists: GetAllListsUseCase
) : CommonViewModel(getAllLists) {

    private val mValidationSuccessFlow = MutableSharedFlow<Validation>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val validationSuccessFlow = mValidationSuccessFlow.asSharedFlow()

    private val mTaskDetailsFlow = MutableStateFlow(TaskDetails())
    val taskDetailsFlow = mTaskDetailsFlow.asStateFlow()

    private var mInitialTask: TaskUI? = null

    fun initState(initial: TaskUI?) = initial?.let {
        Log.d("PRIORITY", initial.toString())
        mTaskDetailsFlow.value = TaskDetails(
            event = TaskDetailsEvent.Initial,
            date = it.taskDate,
            time = it.taskTime,
            isActive = it.isActive,
            priority = it.priority,
            descriptionText = it.description,
            titleText = it.title,
            list = findListById(it.listId)
        )
        mInitialTask = initial
    }

    private fun findListById(id: Long) = listsFlow.value.data?.find {  it.id == id }

    fun validate(title: String?) {
        viewModelScope.launch {
            taskDetailsFlow.value.also {
                val isValid = validateTaskInput(
                    NewTaskParams(
                        title = title,
                        notes = it.descriptionText,
                        listId = it.list?.id
                    )
                )
                if (isValid) {
                    val task = Task(
                        id = mInitialTask?.id ?: 0,
                        title = title ?: "",
                        note = it.descriptionText ?: "",
                        listId = it.list?.id ?: 1,
                        listColor = it.list?.color ?: 0,
                        taskDate = it.date,
                        taskTime = it.time,
                        isActive = it.isActive,
                        priority = it.priority?.toDomain()
                    )
                    if (mInitialTask != null)
                        updateTask(task)
                    else
                        createTask(task)
                }
                mValidationSuccessFlow.tryEmit(Validation(isValid = isValid))
            }
        }
    }

    fun changeDate(date: LocalDate?) {
        mTaskDetailsFlow.value = mTaskDetailsFlow.value.copy(
            date = date,
            event = TaskDetailsEvent.DateChanged
        )
    }

    fun changeTime(time: LocalTime?) {
        mTaskDetailsFlow.value = mTaskDetailsFlow.value.copy(
            time = time,
            event = TaskDetailsEvent.TimeChanged
        )
    }

    fun changeActivity(isActive: Boolean) {
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

    fun changePriority(priority: PriorityUI?) {
        mTaskDetailsFlow.value = mTaskDetailsFlow.value.copy(
            event = TaskDetailsEvent.PriorityChanged,
            priority = priority
        )
    }
}