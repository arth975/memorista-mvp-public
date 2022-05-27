package com.app.toalarm.domain.usecases.task

import com.app.toalarm.domain.models.Task
import com.app.toalarm.domain.repositories.TaskRepository

class CreateTaskUseCase(private val taskRepository: TaskRepository) {

    suspend operator fun invoke(task: Task) {
        taskRepository.add(task)
    }
}