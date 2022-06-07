package com.app.memorista.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.memorista.domain.usecases.list.GetAllListsUseCase
import com.app.memorista.domain.utils.ResultOf
import com.app.memorista.mappers.toUI
import com.app.memorista.models.TaskListUI
import com.app.memorista.utils.models.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

open class CommonViewModel(
    protected val getAllListsUseCase: GetAllListsUseCase
) : ViewModel() {

    private val mListsFlow = MutableStateFlow<Resource<List<TaskListUI>>>(Resource.loading())
    val listsFlow = mListsFlow.asStateFlow()

    init {
        fetchLists()
    }

    private fun fetchLists() {
        viewModelScope.launch {
            when (val result = getAllListsUseCase()) {
                is ResultOf.Success -> {
                    var uiCategories: List<TaskListUI>
                    result.data.collect { categories ->
                        uiCategories = categories.map { it.toUI() }
                        mListsFlow.value = Resource.success(uiCategories)
                    }
                }
                is ResultOf.Error ->
                    mListsFlow.value = Resource.error(result.exception, result.message)
            }
        }
    }

}