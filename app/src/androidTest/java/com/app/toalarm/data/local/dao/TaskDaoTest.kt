package com.app.toalarm.data.local.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.app.toalarm.data.local.LocalDatabase
import com.app.toalarm.data.local.entities.Task
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime

/**
 * @ClassName: TaskDaoTest
 * @Description: class description
 * @Author: Arthur Galoyan
 * @Date: 4/2/2022 10:24 PM
 */
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class TaskDaoTest {

    private lateinit var mDatabase: LocalDatabase
    private lateinit var mTaskDao: TaskDao

    private val mTaskItem = Task("title", "note", LocalDateTime.now(), id = 1)

    @Before
    fun setup() {
        mDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            LocalDatabase::class.java
        ).allowMainThreadQueries().build()
        mTaskDao = mDatabase.getTaskDao()
    }

    @After
    fun tearDown() {
        mDatabase.close()
    }

    private suspend fun assertThatContains(taskItem: Task) {
        val allTaskItems = mTaskDao.observeAllTaskItems().take(1).toList()
        assertThat(allTaskItems[0]).contains(taskItem)
    }

    private suspend fun insertTask(): Task {
        val taskItem = mTaskItem
        mTaskDao.insertTaskItem(taskItem)
        return taskItem
    }

    private suspend fun insertTaskWithTimestamp(): Task {
        val taskItem = mTaskItem
        mTaskDao.insertTaskItemWithTimestamp(taskItem)
        return taskItem
    }

    @Test
    fun insertTaskItem() = runTest {
        val taskItem = insertTask()
        assertThatContains(taskItem)
    }

    @Test
    fun insertTaskItemWithTimestamp() = runTest {
        val taskItem = insertTaskWithTimestamp()
        assertThatContains(taskItem)
    }

    @Test
    fun updateTaskItem() = runTest {
        val taskItem = insertTask()
        mTaskDao.updateTaskItem(taskItem)
        assertThatContains(taskItem)
    }

    @Test
    fun updateTaskItemWithTimestamp() = runTest {
        val taskItem = insertTaskWithTimestamp()
        mTaskDao.updateTaskItemWithTimestamp(taskItem)
        assertThatContains(taskItem)
    }

    @Test
    fun deleteTaskItem() = runTest {
        val taskItem = insertTask()
        mTaskDao.deleteTaskItem(taskItem)

        val allTaskItems = mTaskDao.observeAllTaskItems().take(1).toList()
        assertThat(allTaskItems[0]).doesNotContain(taskItem)
    }

}