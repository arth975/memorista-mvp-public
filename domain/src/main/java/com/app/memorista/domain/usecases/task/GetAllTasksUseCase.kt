package com.app.memorista.domain.usecases.task

import com.app.memorista.domain.models.Task
import com.app.memorista.domain.repositories.TaskRepository
import kotlinx.coroutines.flow.Flow

class GetAllTasksUseCase(
    private val taskRepository: TaskRepository
) {

    operator fun invoke(): Flow<List<Task>> = taskRepository.getAll()
}