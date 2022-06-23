package com.app.memorista.domain.usecases.list

import com.app.memorista.domain.models.ListWithTasks
import com.app.memorista.domain.models.calculateCompletedTasksPercent
import com.app.memorista.domain.models.params.TaskParams
import com.app.memorista.domain.repositories.ListRepository
import com.app.memorista.domain.utils.BaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.CancellationException

class GetListWithTasksUseCase(private val listRepository: ListRepository) {

    suspend operator fun invoke(id: Long): BaseResult<Flow<ListWithTasks>> {
        return try {
            val flow = listRepository.getListWithTasksFlow(TaskParams(listId = id))
                .map {
                    it.list.calculateCompletedTasksPercent()
                    it
                }
            BaseResult.success(flow)
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            BaseResult.error(e, e.message)
        }
    }
}