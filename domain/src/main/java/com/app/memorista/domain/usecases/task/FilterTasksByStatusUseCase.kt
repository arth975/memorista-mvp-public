package com.app.memorista.domain.usecases.task

import com.app.memorista.domain.models.Task
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class FilterTasksByStatusUseCase(
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    suspend operator fun invoke(tasks: List<Task>, isActive: Boolean): List<Task> = coroutineScope {
        val result = async(coroutineDispatcher) {
            tasks.filter { it.isActive == isActive }
        }
        result.await()
    }
}