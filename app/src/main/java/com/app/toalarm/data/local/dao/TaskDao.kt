package com.app.toalarm.data.local.dao

import androidx.room.*
import com.app.toalarm.data.local.entities.Task
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

/**
 * @ClassName: TaskDao
 * @Description: class description
 * @Author: Arthur Galoyan
 * @Date: 4/2/2022 9:51 AM
 */
@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks")
    fun observeAllTaskItems(): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTaskItem(task: Task)

    suspend fun insertTaskItemWithTimestamp(task: Task) {
        insertTaskItem(task = task.apply {
            createdAt = LocalDateTime.now()
            updatedAt = LocalDateTime.now()
        })
    }

    @Delete
    suspend fun deleteTaskItem(task: Task)

    @Update
    suspend fun updateTaskItem(task: Task)

    suspend fun updateTaskItemWithTimestamp(task: Task) {
        updateTaskItem(task = task.apply {
            updatedAt = LocalDateTime.now()
        })
    }
}