package com.app.memorista.di.modules

import com.app.memorista.data.local.LocalDatabase
import com.app.memorista.data.local.dao.ListDao
import com.app.memorista.data.local.dao.TaskDao
import com.app.memorista.data.local.dao.UserDao
import com.app.memorista.data.repositories.ListRepositoryImpl
import com.app.memorista.data.repositories.TaskRepositoryImpl
import com.app.memorista.data.repositories.UserRepositoryImpl
import com.app.memorista.data.repositories.sources.*
import com.app.memorista.domain.repositories.ListRepository
import com.app.memorista.domain.repositories.TaskRepository
import com.app.memorista.domain.repositories.UserRepository
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    single { LocalDatabase.getInstance(context = get()) }

    //Dao
    single { provideTaskDao(localDatabase = get()) }
    single { provideUserDao(localDatabase = get()) }
    single { provideCategoryDao(localDatabase = get()) }

    //Data sources
    single { TaskLocalDataSource(taskDao = get()) } bind TaskDataSource::class
    single { UserLocalDataSource(userDao = get()) } bind UserDataSource::class
    single { ListLocalDataSource(listDao = get()) } bind ListDataSource::class

    //Repository implementations
    single { ListRepositoryImpl(localDataSource = get()) } bind ListRepository::class
    single { TaskRepositoryImpl(localDataSource = get()) } bind TaskRepository::class
    single { UserRepositoryImpl(dataSource = get()) } bind UserRepository::class

}

private fun provideTaskDao(localDatabase: LocalDatabase): TaskDao = localDatabase.getTaskDao()
private fun provideUserDao(localDatabase: LocalDatabase): UserDao = localDatabase.getUserDao()
private fun provideCategoryDao(localDatabase: LocalDatabase): ListDao = localDatabase.getCategoryDao()