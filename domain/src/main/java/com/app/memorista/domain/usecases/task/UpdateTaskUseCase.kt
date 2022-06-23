package com.app.memorista.domain.usecases.task

import com.app.memorista.domain.models.Task
import com.app.memorista.domain.repositories.TaskRepository
import com.app.memorista.domain.usecases.list.UpdateCompletedTaskCountUseCase
import com.app.memorista.domain.usecases.list.UpdateListTaskCountUseCase

class UpdateTaskUseCase(
    private val taskRepository: TaskRepository,
    private val updateListTaskCount: UpdateListTaskCountUseCase,
    private val updateCompletedTaskCount: UpdateCompletedTaskCountUseCase
) {

    suspend operator fun invoke(task: Task) {
        val oldTask = taskRepository.getById(task.id)
        val oldListId = oldTask.listId
        val newListId = task.listId
        val oldIsActive = oldTask.isActive
        val newIsActive = task.isActive

        if (oldListId != newListId) {
            updateListTaskCount(oldListId, false)
            updateListTaskCount(newListId, true)

            if (oldIsActive)
                updateCompletedTaskCount(oldListId, false)

            if (newIsActive)
                updateCompletedTaskCount(newListId, true)

        } else if (oldIsActive != newIsActive) {
            updateCompletedTaskCount(newListId, newIsActive)
        }

        taskRepository.update(task)
    }
}