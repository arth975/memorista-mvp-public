package com.app.toalarm.domain.usecases.task

import com.app.toalarm.domain.models.Task
import com.app.toalarm.domain.repositories.TaskRepository

class DeleteTaskUseCase(private val taskRepository: TaskRepository) {

    suspend operator fun invoke(task: Task) {
        taskRepository.delete(task)
    }
}