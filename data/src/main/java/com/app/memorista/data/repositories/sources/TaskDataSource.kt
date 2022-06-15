package com.app.memorista.data.repositories.sources

import com.app.memorista.data.local.entities.TaskEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface TaskDataSource {

    fun getTasksFlowByDate(date: LocalDate): Flow<List<TaskEntity>>

    suspend fun addTask(task: TaskEntity)

    suspend fun deleteTask(task: TaskEntity)

    suspend fun updateTask(task: TaskEntity)
}