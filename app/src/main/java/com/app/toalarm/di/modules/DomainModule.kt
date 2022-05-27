package com.app.toalarm.di.modules

import com.app.toalarm.data.repositories.CategoryRepositoryImpl
import com.app.toalarm.data.repositories.TaskRepositoryImpl
import com.app.toalarm.domain.repositories.CategoryRepository
import com.app.toalarm.domain.repositories.TaskRepository
import com.app.toalarm.domain.usecases.category.CreateCategoryUseCase
import com.app.toalarm.domain.usecases.category.FetchAllCategoriesUseCase
import com.app.toalarm.domain.usecases.task.FetchTasksByCategoryIdAndDateUseCase
import com.app.toalarm.domain.usecases.category.FilterTasksByStateUseCase
import com.app.toalarm.domain.usecases.task.CreateTaskUseCase
import com.app.toalarm.domain.usecases.task.ValidateTaskInputUseCase
import org.koin.dsl.bind
import org.koin.dsl.module

val domainModule = module {
    //Repository implementations
    single { CategoryRepositoryImpl(localDataSource = get()) } bind CategoryRepository::class
    single { TaskRepositoryImpl(localDataSource = get()) } bind TaskRepository::class

    //Category's use cases
    single { FetchAllCategoriesUseCase(categoryRepository = get()) }
    single { CreateCategoryUseCase(categoryRepository = get()) }

    //Task's use cases
    single { ValidateTaskInputUseCase() }
    single { FilterTasksByStateUseCase() }
    single { CreateTaskUseCase(taskRepository = get()) }
    single { FetchTasksByCategoryIdAndDateUseCase(taskRepository = get()) }
}