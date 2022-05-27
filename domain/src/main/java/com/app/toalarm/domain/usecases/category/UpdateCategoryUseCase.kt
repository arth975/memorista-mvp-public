package com.app.toalarm.domain.usecases.category

import com.app.toalarm.domain.models.Category
import com.app.toalarm.domain.repositories.CategoryRepository

class UpdateCategoryUseCase(private val categoryRepository: CategoryRepository) {

    suspend operator fun invoke(category: Category) {
        categoryRepository.update(category)
    }
}