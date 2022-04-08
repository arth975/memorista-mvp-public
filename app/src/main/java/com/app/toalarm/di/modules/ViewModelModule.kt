package com.app.toalarm.di.modules

import com.app.toalarm.viewmodel.MainViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { MainViewModel(taskRepository = get()) }
}