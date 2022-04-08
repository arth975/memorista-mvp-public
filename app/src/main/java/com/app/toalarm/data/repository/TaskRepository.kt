package com.app.toalarm.data.repository

import com.app.toalarm.data.local.dao.TaskDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

/**
 * @ClassName: TaskRepository
 * @Description: class description
 * @Author: Arthur Galoyan
 * @Date: 4/2/2022 10:38 AM
 */
class TaskRepository(
    private val dao: TaskDao,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getAllTasks() = dao.observeAllTaskItems()
        .flowOn(coroutineDispatcher)
        .catch { it.printStackTrace() }
}