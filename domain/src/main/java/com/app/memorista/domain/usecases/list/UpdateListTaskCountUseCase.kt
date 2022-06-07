package com.app.memorista.domain.usecases.list

import com.app.memorista.domain.repositories.ListRepository

class UpdateListTaskCountUseCase(
    private val listRepository: ListRepository
) : UpdateListTaskUseCase() {
    suspend operator fun invoke(listId: Long, isIncremented: Boolean) {
        listRepository.getById(listId).also {
            listRepository.update(it.copy(tasksCount = calculateCount(it.tasksCount, isIncremented)))
        }
    }
}