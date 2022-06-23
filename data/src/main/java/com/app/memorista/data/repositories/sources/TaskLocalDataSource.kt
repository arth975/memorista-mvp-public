package com.app.memorista.data.repositories.sources

import com.app.memorista.data.local.dao.TaskDao
import com.app.memorista.data.local.entities.TaskEntity
import com.app.memorista.domain.models.params.TaskByDateParams
import kotlinx.coroutines.flow.Flow

class TaskLocalDataSource(
    private val taskDao: TaskDao
) : TaskDataSource {

    override fun getAllTasks(): Flow<List<TaskEntity>> = taskDao.getAllTasks()

    override fun getTasksFlowByDate(params: TaskByDateParams): Flow<List<TaskEntity>> =
        taskDao.getTasksFlowByDate(params.fromDate, params.toDate)

    override fun getFavoriteTasks(): Flow<List<TaskEntity>> = taskDao.getFavoriteTasks()

    override suspend fun getTaskById(id: Long): TaskEntity = taskDao.getById(id)

    override suspend fun addTask(task: TaskEntity) {
        taskDao.insertTask(task)
    }

    override suspend fun deleteTask(task: TaskEntity) {
        taskDao.deleteTask(task)
    }

    override suspend fun updateTask(task: TaskEntity) {
        taskDao.updateTask(task)
    }
}