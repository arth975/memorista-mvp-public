package com.app.memorista.domain.usecases.list

import com.app.memorista.domain.models.calculateCount
import com.app.memorista.domain.repositories.ListRepository

class UpdateListTaskCountUseCase(
    private val listRepository: ListRepository
){
    suspend operator fun invoke(listId: Long, isIncremented: Boolean) {
        listRepository.getById(listId).also {
            val newTasksCount = it.calculateCount(it.tasksCount, isIncremented)
            listRepository.update(it.copy(tasksCount = newTasksCount))
        }
    }
}