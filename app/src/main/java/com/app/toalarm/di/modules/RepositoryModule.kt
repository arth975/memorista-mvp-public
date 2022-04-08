package com.app.toalarm.di.modules

import com.app.toalarm.data.repository.TaskRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { TaskRepository(dao = get()) }
}