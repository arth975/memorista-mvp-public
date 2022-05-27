package com.app.toalarm.domain.usecases.category

import com.app.toalarm.domain.models.Task
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.streams.toList

class FilterTasksByStateUseCase(
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    suspend operator fun invoke(tasks: List<Task>, isActive: Boolean):
            List<Task> = withContext(coroutineDispatcher) {
        tasks.stream()
            .filter { task -> task.isActive == isActive }
            .toList()
    }
}