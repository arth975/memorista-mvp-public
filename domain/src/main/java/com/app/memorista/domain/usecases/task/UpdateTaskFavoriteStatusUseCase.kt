package com.app.memorista.domain.usecases.task

import com.app.memorista.domain.models.Task
import com.app.memorista.domain.repositories.TaskRepository

class UpdateTaskFavoriteStatusUseCase(
    private val taskRepository: TaskRepository
) {

    suspend operator fun invoke(task: Task, isFavorite: Boolean) {
        taskRepository.update(task.copy(isFavorite = isFavorite))
    }
}