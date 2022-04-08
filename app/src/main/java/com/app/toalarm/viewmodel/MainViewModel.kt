package com.app.toalarm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.toalarm.data.local.entities.Task
import com.app.toalarm.data.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * @ClassName: MainViewModel
 * @Description: class description
 * @Author: Arthur Galoyan
 * @Date: 4/2/2022 10:55 AM
 */
class MainViewModel(private val taskRepository: TaskRepository) : ViewModel() {

    private val mTaskFlow: MutableStateFlow<List<Task>?> = MutableStateFlow(null)
    val taskFlow: StateFlow<List<Task>?>
        get() = mTaskFlow

    fun getAllTasks() {
        viewModelScope.launch {
            taskRepository.getAllTasks().collect {
                mTaskFlow.value = it
            }
        }
    }
}