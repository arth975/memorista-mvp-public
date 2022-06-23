package com.app.memorista.domain.models

data class TaskList(
    val id: Long,
    val name: String,
    val tasksCount: Int,
    val color: Int,
    val completedTaskCount: Int = 0,
    var completedTaskPercent: Int = 0
)

fun TaskList.calculateCompletedTasksPercent() {
    completedTaskPercent =
        if (tasksCount == 0) 0 else ((completedTaskCount.toDouble() / tasksCount) * 100).toInt()
}

fun TaskList.calculateCount(initialCount: Int, isIncremented: Boolean): Int =
    if (isIncremented) initialCount + 1 else initialCount - 1
