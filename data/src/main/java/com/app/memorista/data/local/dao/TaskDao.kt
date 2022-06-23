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

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE taskDate >= :from AND taskDate <= :to ")
    fun getTasksFlowByDate(from: LocalDate, to: LocalDate): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE isFavorite = 1")
    fun getFavoriteTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun getById(id: Long): TaskEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)
}