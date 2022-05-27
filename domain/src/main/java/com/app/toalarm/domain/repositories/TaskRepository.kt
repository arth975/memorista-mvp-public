package com.app.toalarm.domain.repositories

import com.app.toalarm.domain.models.Task
import com.app.toalarm.domain.models.params.TaskParams
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    fun getByCategoryIdAndDate(params: TaskParams): Flow<List<Task>>

    suspend fun add(task: Task)

    suspend fun delete(task: Task)

    suspend fun update(task: Task)
}