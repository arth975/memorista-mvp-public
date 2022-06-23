package com.app.memorista.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.memorista.domain.usecases.task.DeleteTaskUseCase
import com.app.memorista.mappers.toDomain
import com.app.memorista.models.TaskUI
import com.app.memorista.utils.models.TaskAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TaskListViewModel(
    private val deleteTaskUseCase: DeleteTaskUseCase
) : ViewModel() {

    private val mActionFlow = MutableStateFlow<TaskAction>(TaskAction.Initial)
    val actionFlow = mActionFlow.asStateFlow()

    fun deleteTask(task: TaskUI) {
        viewModelScope.launch {
            if(deleteTaskUseCase(task.toDomain()))
                mActionFlow.value = TaskAction.DeleteAction
            else
                mActionFlow.value = TaskAction.Failure("Something went wrong!")
        }
    }
}