package com.app.toalarm.data.repositories.sources

import com.app.toalarm.data.local.entities.TaskEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface TaskLocalDataSource {

    fun getTasksByCategoryIdAndDate(id: Long, date: LocalDate): Flow<List<TaskEntity>>

    suspend fun addTask(task: TaskEntity)

    suspend fun deleteTask(task: TaskEntity)

    suspend fun updateTask(task: TaskEntity)
}