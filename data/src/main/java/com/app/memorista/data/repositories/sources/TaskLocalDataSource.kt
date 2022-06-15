package com.app.memorista.data.repositories.sources

import com.app.memorista.data.local.dao.TaskDao
import com.app.memorista.data.local.entities.TaskEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class TaskLocalDataSource(
    private val taskDao: TaskDao
) : TaskDataSource {

    override fun getTasksFlowByDate(date: LocalDate): Flow<List<TaskEntity>> {
        return taskDao.getTasksFlowByDate(date)
    }

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