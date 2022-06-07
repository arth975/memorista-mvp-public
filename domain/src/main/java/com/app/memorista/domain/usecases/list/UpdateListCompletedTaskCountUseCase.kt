package com.app.memorista.domain.usecases.list

import com.app.memorista.domain.repositories.ListRepository

class UpdateListCompletedTaskCountUseCase(
    private val listRepository: ListRepository
) : UpdateListTaskUseCase() {

    suspend operator fun invoke(listId: Long, isIncremented: Boolean) {
        listRepository.getById(listId).also {
            listRepository
                .update(it.copy(completedTaskPercent = calculateCount(it.completedTaskPercent, true)))
        }
    }
}