package com.app.memorista.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.memorista.domain.usecases.list.GetAllListsUseCase
import com.app.memorista.domain.utils.BaseResult
import com.app.memorista.mappers.toUI
import com.app.memorista.models.TaskListUI
import com.app.memorista.utils.models.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class CommonViewModel(
    protected val getAllLists: GetAllListsUseCase
) : ViewModel() {

    private val mListsFlow = MutableStateFlow<Resource<List<TaskListUI>>>(Resource.loading())
    val listsFlow = mListsFlow.asStateFlow()

    init {
        fetchLists()
    }

    private fun fetchLists() {
        viewModelScope.launch {
            when (val result = getAllLists()) {
                is BaseResult.Success -> {
                    var uiCategories: List<TaskListUI>
                    result.data.collect { categories ->
                        uiCategories = categories.map { it.toUI() }
                        mListsFlow.value = Resource.success(uiCategories)
                    }
                }
                is BaseResult.Error ->
                    mListsFlow.value = Resource.error(result.exception, result.message)
            }
        }
    }

}