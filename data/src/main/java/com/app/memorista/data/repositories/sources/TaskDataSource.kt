package com.app.memorista.data.repositories.sources

import com.app.memorista.data.local.entities.TaskEntity
import com.app.memorista.domain.models.params.TaskByDateParams
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface TaskDataSource {

    fun getAllTasks(): Flow<List<TaskEntity>>

    fun getTasksFlowByDate(params: TaskByDateParams): Flow<List<TaskEntity>>

    fun getFavoriteTasks(): Flow<List<TaskEntity>>

    suspend fun getTaskById(id: Long): TaskEntity

    suspend fun addTask(task: TaskEntity)

    suspend fun deleteTask(task: TaskEntity)

    suspend fun updateTask(task: TaskEntity)
}