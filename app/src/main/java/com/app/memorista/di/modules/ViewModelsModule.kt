package com.app.memorista.di.modules

import com.app.memorista.ui.viewmodels.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel {
        HomeViewModel(
            getAllLists = get(),
            getTasksByDate = get(),
            changeTaskActivity = get()
        )
    }
    viewModel {
        CreateTaskViewModel(
            getAllLists = get(),
            validateTaskInput = get(),
            createTask = get(),
            changeTaskActivity = get()
        )
    }

    viewModel {
        IntroViewModel(
            createUser = get(),
            validateUserName = get()
        )
    }

    viewModel { SharedViewModel() }
    viewModel {
        SingleListViewModel(
            getListWithTasks = get(),
            createList = get(),
            updateList = get(),
            deleteList = get()
        )
    }
}