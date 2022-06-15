package com.app.memorista.ui.viewmodels

import com.app.memorista.domain.usecases.list.GetAllListsUseCase
import com.app.memorista.domain.usecases.task.ChangeTaskActivityStateUseCase
import com.app.memorista.domain.usecases.task.GetTasksByDateUseCase
import com.app.memorista.mappers.toUI
import kotlinx.coroutines.flow.map
import java.time.LocalDate

/**
 * @ClassName: MainViewModel
 * @Description: class description
 * @Author: Arthur Galoyan
 * @Date: 4/2/2022 10:55 AM
 */
class HomeViewModel(
    getAllLists: GetAllListsUseCase,
    changeTaskActivity: ChangeTaskActivityStateUseCase,
    private val getTasksByDate: GetTasksByDateUseCase,
) : CommonViewModel(getAllLists, changeTaskActivity) {

    val tasksFlow by lazy {
        getTasksByDate(LocalDate.now())
            .map { it.map { task -> task.toUI() } }
    }
}