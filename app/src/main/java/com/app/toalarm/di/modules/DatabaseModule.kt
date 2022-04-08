package com.app.toalarm.di.modules

import com.app.toalarm.data.local.LocalDatabase
import com.app.toalarm.data.local.dao.TaskDao
import org.koin.dsl.module

val databaseModule = module {
    single { LocalDatabase.create(context = get()) }
    single { provideTaskDao(localDatabase = get()) }
}

private fun provideTaskDao(localDatabase: LocalDatabase): TaskDao = localDatabase.getTaskDao()