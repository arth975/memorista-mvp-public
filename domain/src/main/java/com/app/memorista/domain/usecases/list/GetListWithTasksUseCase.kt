package com.app.memorista.domain.usecases.list

import com.app.memorista.domain.models.ListWithTasks
import com.app.memorista.domain.models.calculateCompletedTasksPercent
import com.app.memorista.domain.models.params.TaskParams
import com.app.memorista.domain.repositories.ListRepository
import com.app.memorista.domain.utils.ResultOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.CancellationException

class GetListWithTasksUseCase(private val listRepository: ListRepository) {

    suspend operator fun invoke(id: Long): ResultOf<Flow<ListWithTasks>> {
        return try {
            val flow = listRepository.getListWithTasksFlow(TaskParams(listId = id))
                .map {
                    it.list.calculateCompletedTasksPercent()
                    it
                }
            ResultOf.success(flow)
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            ResultOf.error(e, e.message)
        }
    }
}