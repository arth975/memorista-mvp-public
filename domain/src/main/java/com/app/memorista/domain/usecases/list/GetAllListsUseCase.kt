package com.app.memorista.domain.usecases.list

import com.app.memorista.domain.models.TaskList
import com.app.memorista.domain.models.calculateCompletedTasksPercent
import com.app.memorista.domain.repositories.ListRepository
import com.app.memorista.domain.utils.ResultOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.CancellationException

class GetAllListsUseCase(private val listRepository: ListRepository) {

    suspend operator fun invoke(): ResultOf<Flow<List<TaskList>>> {
        return try {
            val flow = listRepository.getAll()
                .map {
                    it.map { list ->
                        list.calculateCompletedTasksPercent()
                        list
                    }
                }
            ResultOf.success(flow)
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            ResultOf.error(e)
        }
    }
}