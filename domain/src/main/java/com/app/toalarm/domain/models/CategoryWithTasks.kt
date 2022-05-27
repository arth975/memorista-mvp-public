package com.app.toalarm.domain.models

data class CategoryWithTasks(
    val categoryId: Long,
    val categoryName: String,
    val tasks: List<Task>
)
