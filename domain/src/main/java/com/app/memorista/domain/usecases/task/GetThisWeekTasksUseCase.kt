package com.app.memorista.domain.usecases.task

import com.app.memorista.domain.models.Task
import com.app.memorista.domain.models.params.TaskByDateParams
import com.app.memorista.domain.repositories.TaskRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.*

class GetThisWeekTasksUseCase(private val taskRepository: TaskRepository) {

    private val FIRST_DAY = 1L
    private val END_DAY = 7L

    operator fun invoke(): Flow<List<Task>> {
        val currentDate = LocalDate.now()
        val startDate = currentDate.with(WeekFields.of(Locale.getDefault()).dayOfWeek(), FIRST_DAY)
        val endDate = currentDate.with(WeekFields.of(Locale.getDefault()).dayOfWeek(), END_DAY)

        return taskRepository.getFlowByDate(
            TaskByDateParams(
                fromDate = startDate,
                toDate = endDate
            )
        )
    }
}