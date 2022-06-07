package com.app.memorista.domain.usecases.list

import com.app.memorista.domain.models.TaskList
import com.app.memorista.domain.repositories.ListRepository

class UpdateListUseCase(private val listRepository: ListRepository) {

    suspend operator fun invoke(taskList: TaskList) {
        listRepository.update(taskList)
    }
}