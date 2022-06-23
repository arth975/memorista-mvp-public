package com.app.memorista.domain.usecases.task

import com.app.memorista.domain.models.Task
import com.app.memorista.domain.models.params.TaskByDateParams
import com.app.memorista.domain.repositories.TaskRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class GetTodayTasksUseCase(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(): Flow<List<Task>> =
        taskRepository.getFlowByDate(TaskByDateParams(fromDate = LocalDate.now()))
}