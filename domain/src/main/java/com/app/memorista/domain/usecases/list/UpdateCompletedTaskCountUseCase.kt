package com.app.memorista.domain.usecases.list

import com.app.memorista.domain.models.calculateCount
import com.app.memorista.domain.repositories.ListRepository

class UpdateCompletedTaskCountUseCase(
    private val listRepository: ListRepository
) {

    suspend operator fun invoke(listId: Long, isIncremented: Boolean) {
        listRepository.getById(listId).also {
            val newCompletedCount = it.calculateCount(it.completedTaskCount, isIncremented)
            listRepository.update(it.copy(completedTaskCount = newCompletedCount))
        }
    }
}