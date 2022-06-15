package com.app.memorista.di.modules

import com.app.memorista.data.local.LocalDatabase
import com.app.memorista.data.local.dao.ListDao
import com.app.memorista.data.local.dao.TaskDao
import com.app.memorista.data.local.dao.UserDao
import com.app.memorista.data.repositories.sources.*
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    single { LocalDatabase.getInstance(context = get()) }

    single { provideTaskDao(localDatabase = get()) }
    single { provideUserDao(localDatabase = get()) }
    single { provideCategoryDao(localDatabase = get()) }

    single { TaskLocalDataSource(taskDao = get()) } bind TaskDataSource::class
    single { UserLocalDataSource(userDao = get()) } bind UserDataSource::class
    single { ListLocalDataSource(listDao = get()) } bind ListDataSource::class
}

private fun provideTaskDao(localDatabase: LocalDatabase): TaskDao = localDatabase.getTaskDao()
private fun provideUserDao(localDatabase: LocalDatabase): UserDao = localDatabase.getUserDao()
private fun provideCategoryDao(localDatabase: LocalDatabase): ListDao = localDatabase.getCategoryDao()