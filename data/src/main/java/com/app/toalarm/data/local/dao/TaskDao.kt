package com.app.toalarm.data.local.dao

import androidx.room.*
import com.app.toalarm.data.local.entities.TaskEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * @ClassName: TaskDao
 * @Description: class description
 * @Author: Arthur Galoyan
 * @Date: 4/2/2022 9:51 AM
 */
@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks WHERE categoryId = :categoryId AND taskDate = :date")
    fun getByCategoryIdAndUseCase(categoryId: Long, date: LocalDate): Flow<List<TaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: TaskEntity)

    @Delete
    suspend fun delete(task: TaskEntity)

    @Update
    suspend fun update(task: TaskEntity)
}