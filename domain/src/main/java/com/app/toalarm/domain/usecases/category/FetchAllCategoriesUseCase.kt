package com.app.toalarm.domain.usecases.category

import com.app.toalarm.domain.models.Category
import com.app.toalarm.domain.repositories.CategoryRepository
import com.app.toalarm.domain.utils.ResultOf
import kotlinx.coroutines.flow.Flow

class FetchAllCategoriesUseCase(private val categoryRepository: CategoryRepository) {

    suspend operator fun invoke(): ResultOf<Flow<List<Category>>> {
        return try {
            ResultOf.success(categoryRepository.getAll())
        } catch (e: Exception) {
            ResultOf.error(e)
        }
    }
}