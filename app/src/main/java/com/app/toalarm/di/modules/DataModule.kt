package com.app.toalarm.di.modules

import com.app.toalarm.data.local.LocalDatabase
import com.app.toalarm.data.local.dao.CategoryDao
import com.app.toalarm.data.local.dao.TaskDao
import com.app.toalarm.data.repositories.sources.CategoryLocalDataSource
import com.app.toalarm.data.repositories.sources.CategoryLocalDataSourceImpl
import com.app.toalarm.data.repositories.sources.TaskLocalDataSource
import com.app.toalarm.data.repositories.sources.TaskLocalDataSourceImpl
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    single { LocalDatabase.getInstance(context = get()) }

    single { provideTaskDao(localDatabase = get()) }
    single { provideCategoryDao(localDatabase = get()) }

    single { TaskLocalDataSourceImpl(taskDao = get()) } bind TaskLocalDataSource::class
    single { CategoryLocalDataSourceImpl(categoryDao = get()) } bind CategoryLocalDataSource::class
}

private fun provideTaskDao(localDatabase: LocalDatabase): TaskDao = localDatabase.getTaskDao()
private fun provideCategoryDao(localDatabase: LocalDatabase): CategoryDao = localDatabase.getCategoryDao()