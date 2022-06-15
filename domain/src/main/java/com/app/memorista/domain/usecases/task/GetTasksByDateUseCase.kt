package com.app.memorista.domain.usecases.task

import com.app.memorista.domain.models.Task
import com.app.memorista.domain.models.params.TaskParams
import com.app.memorista.domain.repositories.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class GetTasksByDateUseCase(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(date: LocalDate): Flow<List<Task>> =
        taskRepository.getFlowByDate(date)
}