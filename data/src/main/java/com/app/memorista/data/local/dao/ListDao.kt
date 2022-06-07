package com.app.memorista.data.local.dao

import androidx.room.*
import com.app.memorista.data.local.entities.ListEntity
import com.app.memorista.data.local.entities.relations.ListWithTasksRelation
import kotlinx.coroutines.flow.Flow

@Dao
interface ListDao {

    @Query("SELECT * FROM lists")
    fun getAllListsFlow(): Flow<List<ListEntity>>

    @Transaction
    @Query("SELECT * FROM lists WHERE id = :id")
    fun getListsWithTasksFlowById(id: Long): Flow<ListWithTasksRelation>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(listEntity: ListEntity): Long

    @Delete
    suspend fun deleteList(listEntity: ListEntity)

    @Update
    suspend fun updateList(listEntity: ListEntity)
}