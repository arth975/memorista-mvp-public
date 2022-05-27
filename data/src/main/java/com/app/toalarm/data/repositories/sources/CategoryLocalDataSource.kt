package com.app.toalarm.data.repositories.sources

import com.app.toalarm.data.local.entities.CategoryEntity
import com.app.toalarm.data.local.entities.relations.CategoryWithTasksRelation
import kotlinx.coroutines.flow.Flow

interface CategoryLocalDataSource {

    suspend fun fetchAllCategories(): Flow<List<CategoryEntity>>

    fun fetchCategoryWithTasksByCategoryId(id: Long): Flow<CategoryWithTasksRelation>

    suspend fun addCategory(categoryEntity: CategoryEntity)

    suspend fun updateCategory(categoryEntity: CategoryEntity)

    suspend fun deleteCategory(categoryEntity: CategoryEntity)
}