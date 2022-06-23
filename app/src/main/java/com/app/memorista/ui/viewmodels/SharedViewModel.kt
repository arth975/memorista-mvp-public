package com.app.memorista.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.memorista.domain.usecases.task.UpdateTaskFavoriteStatusUseCase
import com.app.memorista.domain.usecases.task.UpdateTaskStatusUseCase
import com.app.memorista.mappers.toDomain
import com.app.memorista.models.TaskListUI
import com.app.memorista.models.TaskUI
import kotlinx.coroutines.launch

open class SharedViewModel(
    private val updateTaskStatus: UpdateTaskStatusUseCase,
    private val updateTaskFavoriteStatus: UpdateTaskFavoriteStatusUseCase
) : ViewModel() {

    var selectedList: TaskListUI? = null
    var selectedTask: TaskUI? = null

    fun changeTaskStatus(task: TaskUI, isActive: Boolean) {
        viewModelScope.launch {
            updateTaskStatus(task.toDomain(), isActive)
        }
    }

    fun changeTaskFavoriteStatus(task: TaskUI, isFavorite: Boolean) {
        viewModelScope.launch {
            updateTaskFavoriteStatus(task.toDomain(), isFavorite)
        }
    }
}