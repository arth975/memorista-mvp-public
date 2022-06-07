package com.app.memorista.domain.usecases.list

import com.app.memorista.domain.models.TaskList
import com.app.memorista.domain.repositories.ListRepository
import com.app.memorista.domain.utils.ResultOf
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.CancellationException

class GetAllListsUseCase(private val listRepository: ListRepository) {

    suspend operator fun invoke(): ResultOf<Flow<List<TaskList>>> {
        return try {
            ResultOf.success(listRepository.getAll())
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            ResultOf.error(e)
        }
    }
}