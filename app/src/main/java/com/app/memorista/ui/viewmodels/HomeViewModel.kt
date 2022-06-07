package com.app.memorista.ui.viewmodels

import com.app.memorista.domain.usecases.list.FilterTasksByStateUseCase
import com.app.memorista.domain.usecases.list.GetAllListsUseCase
import com.app.memorista.domain.usecases.task.FetchTasksByCategoryIdAndDateUseCase
import com.app.memorista.models.TaskUI
import com.app.memorista.utils.models.Resource
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 * @ClassName: MainViewModel
 * @Description: class description
 * @Author: Arthur Galoyan
 * @Date: 4/2/2022 10:55 AM
 */
class HomeViewModel(
    getAllListsUseCase: GetAllListsUseCase,
    private val fetchTasksByCategoryIdAndDateUseCase: FetchTasksByCategoryIdAndDateUseCase,
    private val filterByStateUseCase: FilterTasksByStateUseCase
) : CommonViewModel(getAllListsUseCase) {

    private val mTasksFlow = MutableSharedFlow<Resource<List<TaskUI>>>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val tasksFlow = mTasksFlow.asSharedFlow()
}