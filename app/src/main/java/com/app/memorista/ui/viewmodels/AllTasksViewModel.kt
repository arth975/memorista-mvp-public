package com.app.memorista.ui.viewmodels

import com.app.memorista.domain.usecases.list.GetAllListsUseCase
import com.app.memorista.domain.usecases.task.GetAllTasksUseCase
import com.app.memorista.mappers.toUI
import kotlinx.coroutines.flow.map

class AllTasksViewModel(
    private val getAllTasks: GetAllTasksUseCase,
    getAllLists: GetAllListsUseCase
) : CommonViewModel(getAllLists) {

    val tasksFlow by lazy {
        getAllTasks().map { it.map { task -> task.toUI() } }
    }
}