package com.app.memorista.data.repositories

import com.app.memorista.data.mappers.toDomain
import com.app.memorista.data.mappers.toEntity
import com.app.memorista.data.repositories.sources.TaskDataSource
import com.app.memorista.domain.models.Task
import com.app.memorista.domain.models.params.TaskParams
import com.app.memorista.domain.repositories.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepositoryImpl(
    private val localDataSource: TaskDataSource
) : TaskRepository {
    override fun getByListId(params: TaskParams): Flow<List<Task>> {
        return localDataSource.getTasksByCategoryIdAndDate(params.listId, params.date)
            .map { entities -> entities.map { it.toDomain() } }
    }

    override suspend fun add(task: Task) {
        localDataSource.addTask(task.toEntity())
    }

    override suspend fun delete(task: Task) {
        localDataSource.deleteTask(task.toEntity())
    }

    override suspend fun update(task: Task) {
        localDataSource.updateTask(task.toEntity())
    }
}