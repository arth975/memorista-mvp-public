package com.app.memorista.domain.models

data class TaskList(
    val id: Long,
    val name: String,
    val tasksCount: Int,
    val completedTaskPercent: Int,
    val color: Int
)
