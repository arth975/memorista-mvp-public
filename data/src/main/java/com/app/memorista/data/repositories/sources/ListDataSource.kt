package com.app.memorista.data.repositories.sources

import com.app.memorista.data.local.entities.ListEntity
import com.app.memorista.data.local.entities.relations.ListWithTasksRelation
import kotlinx.coroutines.flow.Flow

interface ListDataSource {

    suspend fun fetchAllCategories(): Flow<List<ListEntity>>

    fun fetchCategoryWithTasksByCategoryId(id: Long): Flow<ListWithTasksRelation>

    suspend fun fetchById(id: Long): ListEntity

    suspend fun addList(listEntity: ListEntity): Long

    suspend fun updateList(listEntity: ListEntity)

    suspend fun deleteList(listEntity: ListEntity)
}