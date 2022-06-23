package com.app.memorista.domain.repositories

import com.app.memorista.domain.models.Task
import com.app.memorista.domain.models.params.TaskByDateParams
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    fun getAll(): Flow<List<Task>>

    fun getFlowByDate(params: TaskByDateParams): Flow<List<Task>>

    fun getFavorites(): Flow<List<Task>>

    suspend fun getById(id: Long): Task

    suspend fun add(task: Task)

    suspend fun delete(task: Task)

    suspend fun update(task: Task)
}