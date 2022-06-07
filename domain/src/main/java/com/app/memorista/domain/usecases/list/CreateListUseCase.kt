package com.app.memorista.domain.usecases.list

import com.app.memorista.domain.models.TaskList
import com.app.memorista.domain.models.params.ListParams
import com.app.memorista.domain.repositories.ListRepository

class CreateListUseCase(private val listRepository: ListRepository) {

    suspend operator fun invoke(list: TaskList): Long {
        return listRepository.add(list)
    }
}