package com.app.memorista.di.modules

import com.app.memorista.ui.viewmodels.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel {
        TodayTasksViewModel(
            getAllLists = get(),
            getTodayTasks = get(),
            filterTasksByStatus = get()
        )
    }
    viewModel {
        SingleTaskViewModel(
            createTask = get(),
            updateTask = get(),
            getAllLists = get(),
            validateTaskInput = get()
        )
    }

    viewModel {
        IntroViewModel(
            createUser = get(),
            validateUserName = get()
        )
    }

    viewModel {
        SharedViewModel(
            updateTaskStatus = get(),
            updateTaskFavoriteStatus = get()
        )
    }

    viewModel {
        SingleListViewModel(
            getListWithTasks = get(),
            createList = get(),
            updateList = get(),
            deleteList = get()
        )
    }

    viewModel {
        UpcomingTasksViewModel(
            getUpcomingTasks = get(),
            getAllLists = get()
        )
    }

    viewModel {
        ThisWeekTasksViewModel(
            getAllLists = get(),
            getThisWeekTasks = get(),
            filterTasksByStatus = get()
        )
    }

    viewModel {
        AllTasksViewModel(
            getAllTasks = get(),
            getAllLists = get()
        )
    }

    viewModel {
        FavoriteTasksViewModel(
            getFavoriteTasks = get(),
            updateTaskStatus = get(),
            updateTaskFavoriteStatus = get()
        )
    }

    viewModel {
        TaskListViewModel(
            deleteTaskUseCase = get()
        )
    }
}