package com.app.memorista.domain.repositories

import com.app.memorista.domain.models.TaskList
import com.app.memorista.domain.models.ListWithTasks
import com.app.memorista.domain.models.params.TaskParams
import kotlinx.coroutines.flow.Flow

interface ListRepository {

    suspend fun getAll(): Flow<List<TaskList>>

    suspend fun getById(id: Long): TaskList

    suspend fun getListWithTasksFlow(params: TaskParams): Flow<ListWithTasks>

    suspend fun add(taskList: TaskList): Long

    suspend fun update(taskList: TaskList)

    suspend fun delete(taskList: TaskList)
}