package com.app.memorista.data.repositories

import com.app.memorista.data.mappers.toDomain
import com.app.memorista.data.mappers.toEntity
import com.app.memorista.data.repositories.sources.TaskDataSource
import com.app.memorista.domain.models.Task
import com.app.memorista.domain.models.params.TaskParams
import com.app.memorista.domain.repositories.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class TaskRepositoryImpl(
    private val localDataSource: TaskDataSource
) : TaskRepository {
    override fun getFlowByDate(date: LocalDate): Flow<List<Task>> {
        return localDataSource.getTasksFlowByDate(date)
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