package com.app.toalarm.di.modules

import com.app.toalarm.ui.viewmodels.CreateCategoryDialogViewModel
import com.app.toalarm.ui.viewmodels.CreateTaskViewModel
import com.app.toalarm.ui.viewmodels.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel {
        HomeViewModel(
            fetchAllCategoriesUseCase = get(),
            fetchTasksByCategoryIdAndDateUseCase = get(),
            filterByStateUseCase = get()
        )
    }
    viewModel { CreateCategoryDialogViewModel(createCategoryUseCase = get()) }
    viewModel {
        CreateTaskViewModel(
            fetchAllCategoriesUseCase = get(),
            validateTaskInputUseCase = get(),
            createTaskUseCase = get()
        )
    }
}