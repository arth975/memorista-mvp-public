package com.app.toalarm.data.repositories.sources

import com.app.toalarm.data.local.dao.TaskDao
import com.app.toalarm.data.local.entities.TaskEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class TaskLocalDataSourceImpl(
    private val taskDao: TaskDao
) : TaskLocalDataSource {

    override fun getTasksByCategoryIdAndDate(id: Long, date: LocalDate): Flow<List<TaskEntity>> {
        return taskDao.getByCategoryIdAndUseCase(id, date)
    }

    override suspend fun addTask(task: TaskEntity) {
        taskDao.insert(task)
    }

    override suspend fun deleteTask(task: TaskEntity) {
        taskDao.delete(task)
    }

    override suspend fun updateTask(task: TaskEntity) {
        taskDao.update(task)
    }
}