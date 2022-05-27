package com.app.toalarm.domain.usecases.task

import com.app.toalarm.domain.models.CategoryWithTasks
import com.app.toalarm.domain.models.Task
import com.app.toalarm.domain.models.params.TaskParams
import com.app.toalarm.domain.repositories.CategoryRepository
import com.app.toalarm.domain.repositories.TaskRepository
import com.app.toalarm.domain.utils.ResultOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FetchTasksByCategoryIdAndDateUseCase(
    private val taskRepository: TaskRepository
) {
     operator fun invoke(params: TaskParams): Flow<List<Task>>{//ResultOf<Flow<List<Task>>> {
        return taskRepository.getByCategoryIdAndDate(params)
            .map { tasks -> tasks.sortedByDescending { it.taskDateTime } }
    }
}