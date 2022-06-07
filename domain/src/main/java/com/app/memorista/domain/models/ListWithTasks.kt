package com.app.memorista.domain.models

data class ListWithTasks(
    val list: TaskList,
    val tasks: List<Task>
)
