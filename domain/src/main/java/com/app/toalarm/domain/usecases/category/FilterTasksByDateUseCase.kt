package com.app.toalarm.domain.usecases.category

import com.app.toalarm.domain.models.Task
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import kotlin.streams.toList

class FilterTasksByDateUseCase(
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    suspend operator fun invoke(tasks: List<Task>, date: LocalDate): List<Task> =
        withContext(coroutineDispatcher) {
            tasks.stream()
                .filter { task -> task.taskDateTime.toLocalDate().isEqual(date) }
                .toList()
        }
}