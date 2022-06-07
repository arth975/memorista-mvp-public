package com.app.memorista.data.local.dao

import androidx.room.*
import com.app.memorista.data.local.entities.TaskEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

/**
 * @ClassName: TaskDao
 * @Description: class description
 * @Author: Arthur Galoyan
 * @Date: 4/2/2022 9:51 AM
 */
@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks WHERE taskDate = :date")
    fun getTasksFlowByDate(date: LocalDate): Flow<List<TaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)
}