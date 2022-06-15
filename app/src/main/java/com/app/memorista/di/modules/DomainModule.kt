package com.app.memorista.di.modules

import com.app.memorista.data.repositories.ListRepositoryImpl
import com.app.memorista.data.repositories.TaskRepositoryImpl
import com.app.memorista.data.repositories.UserRepositoryImpl
import com.app.memorista.domain.repositories.ListRepository
import com.app.memorista.domain.repositories.TaskRepository
import com.app.memorista.domain.repositories.UserRepository
import com.app.memorista.domain.usecases.list.*
import com.app.memorista.domain.usecases.task.ChangeTaskActivityStateUseCase
import com.app.memorista.domain.usecases.task.CreateTaskUseCase
import com.app.memorista.domain.usecases.task.GetTasksByDateUseCase
import com.app.memorista.domain.usecases.task.ValidateTaskInputUseCase
import com.app.memorista.domain.usecases.user.CreateUserUseCase
import com.app.memorista.domain.usecases.user.GetUserUseCase
import com.app.memorista.domain.usecases.user.ValidateUserNameUseCase
import org.koin.dsl.bind
import org.koin.dsl.module

val domainModule = module {
    //Repository implementations
    single { ListRepositoryImpl(localDataSource = get()) } bind ListRepository::class
    single { TaskRepositoryImpl(localDataSource = get()) } bind TaskRepository::class
    single { UserRepositoryImpl(dataSource = get()) } bind UserRepository::class

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
    single { CreateTaskUseCase(taskRepository = get(), updateListTaskCount = get(), updateCompletedTaskCount = get()) }
    single { GetTasksByDateUseCase(taskRepository = get()) }
    single { ChangeTaskActivityStateUseCase(taskRepository = get(), updateCompletedTaskCount = get()) }

    //User's use cases
    single { CreateUserUseCase(repo = get()) }
    single { GetUserUseCase(repo = get()) }
    single { ValidateUserNameUseCase() }
}