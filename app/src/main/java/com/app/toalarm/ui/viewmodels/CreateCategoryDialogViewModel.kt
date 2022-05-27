package com.app.toalarm.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.app.toalarm.domain.models.params.CategoryParams
import com.app.toalarm.domain.usecases.category.CreateCategoryUseCase

class CreateCategoryDialogViewModel(
    private val createCategoryUseCase: CreateCategoryUseCase
) : ViewModel() {

    suspend fun createCategory(name: String?): Boolean {
        if(name == null || name.isEmpty())
            return false
        createCategoryUseCase(CategoryParams(name = name))
        return true
    }
}