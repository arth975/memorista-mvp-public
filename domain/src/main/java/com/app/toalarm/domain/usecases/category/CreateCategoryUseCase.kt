package com.app.toalarm.domain.usecases.category

import com.app.toalarm.domain.models.Category
import com.app.toalarm.domain.models.params.CategoryParams
import com.app.toalarm.domain.repositories.CategoryRepository

class CreateCategoryUseCase(private val categoryRepository: CategoryRepository) {

    suspend operator fun invoke(params: CategoryParams) {
        categoryRepository.add(
            Category(
                name = params.name,
                id = 0
            )
        )
    }
}