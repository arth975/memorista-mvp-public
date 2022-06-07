package com.app.memorista.domain.usecases.task

import com.app.memorista.domain.models.Task
import com.app.memorista.domain.models.params.TaskParams
import com.app.memorista.domain.repositories.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FetchTasksByCategoryIdAndDateUseCase(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(params: TaskParams): Flow<List<Task>> =
        taskRepository.getByListId(params)
            .map { tasks -> tasks.sortedByDescending { it.taskDate } }
}