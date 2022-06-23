package com.app.memorista.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.memorista.domain.usecases.list.CreateListUseCase
import com.app.memorista.domain.usecases.list.DeleteListUseCase
import com.app.memorista.domain.usecases.list.GetListWithTasksUseCase
import com.app.memorista.domain.usecases.list.UpdateListUseCase
import com.app.memorista.domain.utils.BaseResult
import com.app.memorista.mappers.toDomain
import com.app.memorista.mappers.toUI
import com.app.memorista.models.ListWithTasksUI
import com.app.memorista.utils.NEW_TASK_LIST
import com.app.memorista.utils.models.Resource
import com.app.memorista.utils.models.TaskListEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SingleListViewModel(
    private val getListWithTasks: GetListWithTasksUseCase,
    private val createList: CreateListUseCase,
    private val updateList: UpdateListUseCase,
    private val deleteList: DeleteListUseCase
) : ViewModel() {

    private val mListWithTasksFlow = MutableStateFlow<Resource<ListWithTasksUI>>(Resource.empty())
    val listWithTasksFlow = mListWithTasksFlow.asStateFlow()

    private val mEventFlow =
        MutableStateFlow<TaskListEvent>(TaskListEvent.initial(null))
    val eventFlow = mEventFlow.asStateFlow()

    fun fetchListWithTasks(id: Long) {
        viewModelScope.launch {
            when (val result = getListWithTasks(id)) {
                is BaseResult.Success ->
                    result.data.collect { listWithTasks ->
                        mListWithTasksFlow.value = Resource.success(listWithTasks.toUI())
                        mEventFlow.value = TaskListEvent.initial(listWithTasks.list.toUI())
                    }
                is BaseResult.Error ->
                    mListWithTasksFlow.value = Resource.error(result.exception)
            }
        }
    }

    fun createNew() {
        viewModelScope.launch {
            fetchListWithTasks(createList(list = NEW_TASK_LIST))
        }
    }

    fun delete() {
        viewModelScope.launch {
            listWithTasksFlow.value.data?.list?.let {
                deleteList(it.toDomain())
            }
        }
    }

    fun update() {
        viewModelScope.launch {
            eventFlow.value.data?.let {
                updateList(it.toDomain())
            }
        }
    }

    fun updateName(name: String?) {
        if (!name.isNullOrEmpty()) {
            val updatedList = mEventFlow.value.data?.copy(name = name)
            mEventFlow.value = TaskListEvent.nameChanged(updatedList)
        }
    }

    fun updateColor(color: Int?) {
        color?.let {
            val updatedList = mEventFlow.value.data?.copy(color = it)
            mEventFlow.value = TaskListEvent.colorChanged(updatedList)
        }
    }
}