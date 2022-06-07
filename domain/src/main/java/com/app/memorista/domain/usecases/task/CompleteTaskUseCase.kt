package com.app.memorista.domain.usecases.task

import com.app.memorista.domain.models.Task
import com.app.memorista.domain.repositories.TaskRepository
import com.app.memorista.domain.usecases.list.UpdateListCompletedTaskCountUseCase

class CompleteTaskUseCase(
    private val taskRepository: TaskRepository,
    private val updateListCompletedTaskCount: UpdateListCompletedTaskCountUseCase
) {

    suspend operator fun invoke(task: Task) {
        taskRepository.update(task.copy(isActive = true))
        updateListCompletedTaskCount(task.listId, false)
    }
}