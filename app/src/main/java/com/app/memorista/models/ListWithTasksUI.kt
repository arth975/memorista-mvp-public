package com.app.memorista.models

data class ListWithTasksUI(
    val list: TaskListUI,
    val tasks: List<TaskUI>
)