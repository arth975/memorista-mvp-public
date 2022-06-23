package com.app.memorista.ui.viewmodels

import com.app.memorista.domain.usecases.list.GetAllListsUseCase
import com.app.memorista.domain.usecases.task.GetUpcomingTasksUseCase
import com.app.memorista.mappers.toUI
import kotlinx.coroutines.flow.map

class UpcomingTasksViewModel(
    private val getUpcomingTasks: GetUpcomingTasksUseCase,
    getAllLists: GetAllListsUseCase
) : CommonViewModel(getAllLists) {

    val tasksFlow by lazy {
        getUpcomingTasks.invoke().map { tasks -> tasks.map { it.toUI() } }
    }
}