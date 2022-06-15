package com.app.memorista.mappers

import com.app.memorista.domain.models.*
import com.app.memorista.models.*
import java.time.LocalTime

fun TaskList.toUI() = TaskListUI(
    name = name,
    id = id,
    tasksCount = tasksCount,
    completedTaskCount = completedTaskCount,
    completedTasksPercent = completedTaskPercent,
    color = color
)

fun TaskListUI.toDomain() = TaskList(
    name = name,
    id = id,
    tasksCount = tasksCount,
    completedTaskCount = completedTaskCount,
    completedTaskPercent = completedTasksPercent,
    color = color
)

fun Task.toUI() = TaskUI(
    id = id,
    title = title,
    listId = listId,
    listColor = listColor,
    description = note,
    taskTime = taskTime,
    taskDate = taskDate,
    isActive = isActive,
    alarmTime = LocalTime.now(),
    priority = if(priority != null) PriorityUI.values()[priority!!.ordinal] else null
)

fun TaskUI.toDomain() = Task(
    title = title,
    note = description,
    taskDate = taskDate,
    listId = listId,
    listColor = listColor,
    isActive = isActive,
    alarmTime = alarmTime,
    priority = if(priority != null) Priority.values()[priority.ordinal] else null,
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