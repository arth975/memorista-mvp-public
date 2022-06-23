package com.app.memorista.ui.viewmodels

import com.app.memorista.domain.usecases.task.GetFavoriteTasksUseCase
import com.app.memorista.domain.usecases.task.UpdateTaskFavoriteStatusUseCase
import com.app.memorista.domain.usecases.task.UpdateTaskStatusUseCase
import com.app.memorista.mappers.toUI
import kotlinx.coroutines.flow.map

class FavoriteTasksViewModel(
    private val getFavoriteTasks: GetFavoriteTasksUseCase,
    updateTaskStatus: UpdateTaskStatusUseCase,
    updateTaskFavoriteStatus: UpdateTaskFavoriteStatusUseCase
) : SharedViewModel(updateTaskStatus, updateTaskFavoriteStatus) {

    val favoriteTasks by lazy {
        getFavoriteTasks().map { tasks -> tasks.map { it.toUI() } }
    }
}