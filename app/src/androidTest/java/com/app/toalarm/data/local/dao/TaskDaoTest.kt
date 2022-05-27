package com.app.toalarm.data.local.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.app.toalarm.data.local.LocalDatabase
import com.app.toalarm.data.local.entities.CategoryEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

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
    private lateinit var mCategoryDao: CategoryDao

    //private val mTaskItem = TaskEntity("title", "note", LocalDateTime.now(), id = 1)

    @Before
    fun setup() {
        mDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            LocalDatabase::class.java
        ).allowMainThreadQueries().build()
        mCategoryDao = mDatabase.getCategoryDao()
    }

    @After
    fun tearDown() {
        mDatabase.close()
    }

    @Test
    fun test() = runBlocking {
        val categoryEntity = CategoryEntity(
            name = "Category",
            id = 1
        )
        //mCategoryDao.insert(categoryEntity)
        val result = mCategoryDao.getAllCategories()
    }
/*

    private suspend fun assertThatContains(taskItem: TaskEntity) {
        val allTaskItems = mTaskDao.observeAllTaskItems().take(1).toList()
        assertThat(allTaskItems[0]).contains(taskItem)
    }

    private suspend fun insertTask(): TaskEntity {
        val taskItem = mTaskItem
        mTaskDao.insertTaskItem(taskItem)
        return taskItem
    }

    private suspend fun insertTaskWithTimestamp(): TaskEntity {
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
*/

}