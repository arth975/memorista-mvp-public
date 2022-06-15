package com.app.memorista.models

data class TaskListUI(
    val id: Long,
    val name: String,
    val tasksCount: Int,
    val completedTaskCount: Int,
    val completedTasksPercent: Int,
    val color: Int
)
