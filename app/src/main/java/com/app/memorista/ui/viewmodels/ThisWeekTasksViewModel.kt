package com.app.memorista.ui.viewmodels

import com.app.memorista.domain.usecases.list.GetAllListsUseCase
import com.app.memorista.domain.usecases.task.FilterTasksByStatusUseCase
import com.app.memorista.domain.usecases.task.GetThisWeekTasksUseCase
import com.app.memorista.mappers.toUI
import kotlinx.coroutines.flow.map

class ThisWeekTasksViewModel(
    private val getThisWeekTasks: GetThisWeekTasksUseCase,
    private val filterTasksByStatus: FilterTasksByStatusUseCase,
    getAllLists: GetAllListsUseCase
) : CommonViewModel(getAllLists) {

    val tasksFlow by lazy {
        getThisWeekTasks().map {
            filterTasksByStatus(it, false).map { task -> task.toUI() }
        }
    }

    val completedTasksFlow by lazy {
        getThisWeekTasks().map {
            filterTasksByStatus(it, true).map { task -> task.toUI() }
        }
    }
}