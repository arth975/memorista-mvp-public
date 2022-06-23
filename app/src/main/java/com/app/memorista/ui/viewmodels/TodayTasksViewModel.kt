package com.app.memorista.ui.viewmodels

import com.app.memorista.domain.usecases.list.GetAllListsUseCase
import com.app.memorista.domain.usecases.task.FilterTasksByStatusUseCase
import com.app.memorista.domain.usecases.task.GetTodayTasksUseCase
import com.app.memorista.mappers.toUI
import kotlinx.coroutines.flow.map

/**
 * @ClassName: MainViewModel
 * @Description: class description
 * @Author: Arthur Galoyan
 * @Date: 4/2/2022 10:55 AM
 */
class TodayTasksViewModel(
    private val getTodayTasks: GetTodayTasksUseCase,
    private val filterTasksByStatus: FilterTasksByStatusUseCase,
    getAllLists: GetAllListsUseCase
) : CommonViewModel(getAllLists) {

    val tasksFlow by lazy {
        getTodayTasks().map {
            filterTasksByStatus(it, false).map { task -> task.toUI() }
        }
    }

    val completedTasksFlow by lazy {
        getTodayTasks().map {
            filterTasksByStatus(it, true).map { task -> task.toUI() }
        }
    }

}