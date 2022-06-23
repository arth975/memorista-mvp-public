package com.app.memorista.domain.usecases.task

import com.app.memorista.domain.models.Task
import com.app.memorista.domain.repositories.TaskRepository
import com.app.memorista.domain.usecases.list.UpdateCompletedTaskCountUseCase

class UpdateTaskStatusUseCase(
    private val taskRepository: TaskRepository,
    private val updateCompletedTaskCount: UpdateCompletedTaskCountUseCase
) {
    suspend operator fun invoke(task: Task, isActive: Boolean) {
        taskRepository.update(task.copy(isActive = isActive))
        updateCompletedTaskCount(task.listId, isActive)
    }
}