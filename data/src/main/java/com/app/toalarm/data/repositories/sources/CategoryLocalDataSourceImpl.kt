package com.app.toalarm.data.repositories.sources

import com.app.toalarm.data.local.dao.CategoryDao
import com.app.toalarm.data.local.entities.CategoryEntity
import com.app.toalarm.data.local.entities.relations.CategoryWithTasksRelation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CategoryLocalDataSourceImpl(
    private val categoryDao: CategoryDao
) : CategoryLocalDataSource {

    override suspend fun fetchAllCategories(): Flow<List<CategoryEntity>> {
        var data: Flow<List<CategoryEntity>> = flow{}
        try {
            data = categoryDao.getAllCategories()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return data
    }

    override fun fetchCategoryWithTasksByCategoryId(id: Long): Flow<CategoryWithTasksRelation> =
        categoryDao.getCategoryWithTasksObserverById(id)

    override suspend fun addCategory(categoryEntity: CategoryEntity) {
        categoryDao.insert(categoryEntity)
    }

    override suspend fun updateCategory(categoryEntity: CategoryEntity) {
        categoryDao.update(categoryEntity)
    }

    override suspend fun deleteCategory(categoryEntity: CategoryEntity) {
        categoryDao.delete(categoryEntity)
    }

}