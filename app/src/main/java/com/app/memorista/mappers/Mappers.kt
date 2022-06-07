package com.app.memorista.mappers

import com.app.memorista.domain.models.ListWithTasks
import com.app.memorista.domain.models.Task
import com.app.memorista.domain.models.TaskList
import com.app.memorista.domain.models.User
import com.app.memorista.models.ListWithTasksUI
import com.app.memorista.models.TaskListUI
import com.app.memorista.models.TaskUI
import com.app.memorista.models.UserUI
import java.time.LocalTime

fun TaskList.toUI() = TaskListUI(
    name = this.name,
    id = this.id,
    tasksCount = tasksCount,
    completedTasksPercent = completedTaskPercent,
    color = color
)

fun TaskListUI.toDomain() = TaskList(
    name = this.name,
    id = this.id,
    tasksCount = tasksCount,
    completedTaskPercent = completedTasksPercent,
    color = color
)

fun Task.toUI() = TaskUI(
    title = title,
    description = note,
    taskTime = taskTime,
    taskDate = taskDate,
    isActive = isActive,
    alarmTime = LocalTime.now(),
    categoryId = listId,
    id = id
)

fun TaskUI.toDomain() = Task(
    title = title,
    note = description,
    taskDate = taskDate,
    listId = categoryId,
    isActive = isActive,
    alarmTime = alarmTime,
    id = id
)

fun UserUI.toDomain() = User(
    id = 0,
    name = name,
    photoUri = photoUri
)

fun ListWithTasks.toUI() = ListWithTasksUI(
    list = list.toUI(),
    tasks = tasks.map { it.toUI() }
)