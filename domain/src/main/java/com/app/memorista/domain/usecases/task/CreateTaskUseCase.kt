package com.app.memorista.domain.usecases.task

import com.app.memorista.domain.models.Task
import com.app.memorista.domain.repositories.ListRepository
import com.app.memorista.domain.repositories.TaskRepository
import com.app.memorista.domain.usecases.list.UpdateCompletedTaskCountUseCase
import com.app.memorista.domain.usecases.list.UpdateListTaskCountUseCase

class CreateTaskUseCase(
    private val taskRepository: TaskRepository,
    private val updateListTaskCount: UpdateListTaskCountUseCase,
    private val updateCompletedTaskCount: UpdateCompletedTaskCountUseCase
) {

    suspend operator fun invoke(task: Task) {
        taskRepository.add(task)
        updateListTaskCount(task.listId, true)
        if(task.isActive)
            updateCompletedTaskCount(task.listId, true)
    }
}