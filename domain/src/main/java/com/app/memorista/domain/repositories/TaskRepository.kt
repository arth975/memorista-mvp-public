package com.app.memorista.domain.repositories

import com.app.memorista.domain.models.Task
import com.app.memorista.domain.models.params.TaskParams
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface TaskRepository {

    fun getFlowByDate(date: LocalDate): Flow<List<Task>>

    suspend fun add(task: Task)

    suspend fun delete(task: Task)

    suspend fun update(task: Task)
}