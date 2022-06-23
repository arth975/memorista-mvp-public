package com.app.memorista.domain.usecases.task

import com.app.memorista.domain.models.Task
import com.app.memorista.domain.repositories.TaskRepository
import java.util.concurrent.CancellationException

class DeleteTaskUseCase(private val taskRepository: TaskRepository) {

    suspend operator fun invoke(task: Task): Boolean = try {
        taskRepository.delete(task)
        true
    } catch (e: Exception) {
        if (e is CancellationException) throw e
        false
    }
}