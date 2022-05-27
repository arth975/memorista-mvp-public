package com.app.toalarm.data.local.dao

import androidx.room.*
import com.app.toalarm.data.local.entities.CategoryEntity
import com.app.toalarm.data.local.entities.relations.CategoryWithTasksRelation
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Query("SELECT * FROM categories")
    fun getAllCategories(): Flow<List<CategoryEntity>>

    @Transaction
    @Query("SELECT * FROM categories WHERE id = :id")
    fun getCategoryWithTasksObserverById(id: Long): Flow<CategoryWithTasksRelation>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: CategoryEntity)

    @Delete
    suspend fun delete(entity: CategoryEntity)

    @Update
    suspend fun update(entity: CategoryEntity)
}