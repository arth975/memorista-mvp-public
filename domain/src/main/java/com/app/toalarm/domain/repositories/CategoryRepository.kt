package com.app.toalarm.domain.repositories

import com.app.toalarm.domain.models.Category
import com.app.toalarm.domain.models.CategoryWithTasks
import com.app.toalarm.domain.models.params.TaskParams
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    suspend fun getAll(): Flow<List<Category>>

    suspend fun getTasksByCategoryIdAndDate(params: TaskParams): Flow<CategoryWithTasks>

    suspend fun add(category: Category)

    suspend fun update(category: Category)

    suspend fun delete(category: Category)
}