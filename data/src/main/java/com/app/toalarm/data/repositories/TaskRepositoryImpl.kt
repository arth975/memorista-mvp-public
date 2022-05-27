package com.app.toalarm.data.repositories

import com.app.toalarm.data.mappers.toDomain
import com.app.toalarm.data.mappers.toEntity
import com.app.toalarm.data.repositories.sources.TaskLocalDataSource
import com.app.toalarm.domain.models.Task
import com.app.toalarm.domain.models.params.TaskParams
import com.app.toalarm.domain.repositories.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepositoryImpl(
    private val localDataSource: TaskLocalDataSource
) : TaskRepository {
    override fun getByCategoryIdAndDate(params: TaskParams): Flow<List<Task>> {
        return localDataSource.getTasksByCategoryIdAndDate(params.categoryId, params.taskDate)
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