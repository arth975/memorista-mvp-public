package com.app.toalarm.data.repositories

import com.app.toalarm.data.local.entities.TaskEntity
import com.app.toalarm.data.mappers.toDomain
import com.app.toalarm.data.mappers.toEntity
import com.app.toalarm.data.repositories.sources.CategoryLocalDataSource
import com.app.toalarm.domain.models.Category
import com.app.toalarm.domain.models.CategoryWithTasks
import com.app.toalarm.domain.models.Task
import com.app.toalarm.domain.models.params.TaskParams
import com.app.toalarm.domain.repositories.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class CategoryRepositoryImpl(
    private val localDataSource: CategoryLocalDataSource
) : CategoryRepository {

    override suspend fun getAll(): Flow<List<Category>> =
        localDataSource.fetchAllCategories().map { list -> list.map { it.toDomain() } }

    override suspend fun getTasksByCategoryIdAndDate(params: TaskParams): Flow<CategoryWithTasks> =
        localDataSource
            .fetchCategoryWithTasksByCategoryId(params.categoryId)
            .map { categoryWithTasks ->
                CategoryWithTasks(
                    categoryId = categoryWithTasks.category.id,
                    categoryName = categoryWithTasks.category.name,
                    tasks = filterTasks(categoryWithTasks.tasks, params.taskDate)
                )
            }


    override suspend fun add(category: Category) {
        localDataSource.addCategory(category.toEntity())
    }

    override suspend fun update(category: Category) {
        localDataSource.updateCategory(category.toEntity())
    }

    override suspend fun delete(category: Category) {
        localDataSource.deleteCategory(category.toEntity())
    }

    private fun filterTasks(entities: List<TaskEntity>, date: LocalDate): List<Task> =
        entities.map { taskEntity -> taskEntity.toDomain() }
            .filter { task -> task.taskDateTime.toLocalDate().isEqual(date) }
}