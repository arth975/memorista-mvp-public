package com.app.memorista.di.modules

import com.app.memorista.domain.usecases.list.*
import com.app.memorista.domain.usecases.task.*
import com.app.memorista.domain.usecases.user.CreateUserUseCase
import com.app.memorista.domain.usecases.user.GetUserUseCase
import com.app.memorista.domain.usecases.user.ValidateUserNameUseCase
import org.koin.dsl.module

val domainModule = module {
    //List's use cases
    single { GetAllListsUseCase(listRepository = get()) }
    single { CreateListUseCase(listRepository = get()) }
    single { UpdateListUseCase(listRepository = get()) }
    single { DeleteListUseCase(listRepository = get()) }
    single { UpdateListTaskCountUseCase(listRepository = get()) }
    single { GetListWithTasksUseCase(listRepository = get()) }
    single { UpdateCompletedTaskCountUseCase(listRepository = get()) }

    //Task's use cases
    single { ValidateTaskInputUseCase() }
    single {
        CreateTaskUseCase(
            taskRepository = get(),
            updateListTaskCount = get(),
            updateCompletedTaskCount = get()
        )
    }
    single {
        UpdateTaskUseCase(
            taskRepository = get(),
            updateListTaskCount = get(),
            updateCompletedTaskCount = get()
        )
    }
    single { DeleteTaskUseCase(taskRepository = get()) }
    single { GetTodayTasksUseCase(taskRepository = get()) }
    single { GetFavoriteTasksUseCase(taskRepository = get()) }
    single { GetUpcomingTasksUseCase(taskRepository = get()) }
    single { GetThisWeekTasksUseCase(taskRepository = get()) }
    single { GetAllTasksUseCase(taskRepository = get()) }
    single { FilterTasksByStatusUseCase() }
    single { UpdateTaskStatusUseCase(taskRepository = get(), updateCompletedTaskCount = get()) }
    single { UpdateTaskFavoriteStatusUseCase(taskRepository = get()) }

    //User's use cases
    single { CreateUserUseCase(repo = get()) }
    single { GetUserUseCase(repo = get()) }
    single { ValidateUserNameUseCase() }
}