package com.app.memorista.data.mappers

import com.app.memorista.data.local.entities.ListEntity
import com.app.memorista.data.local.entities.TaskEntity
import com.app.memorista.data.local.entities.UserEntity
import com.app.memorista.domain.models.Priority
import com.app.memorista.domain.models.Task
import com.app.memorista.domain.models.TaskList
import com.app.memorista.domain.models.User

fun ListEntity.toDomain() = TaskList(
    id = id,
    name = name,
    tasksCount = tasksCount,
    completedTaskCount = completedTasksPercent,
    color = color
)

fun TaskList.toEntity() = ListEntity(
    id = id,
    name = name,
    tasksCount = tasksCount,
    completedTasksPercent = completedTaskCount,
    color = color
)

fun TaskEntity.toDomain() = Task(
    id = id,
    listId = listId,
    listColor = listColor,
    title = title,
    note = description,
    taskDate = taskDate,
    taskTime = taskTime,
    alarmTime = alarmTime,
    isActive = isActive,
    isFavorite = isFavorite,
    priority = if (priorityCode != null) Priority.values()[priorityCode!!] else null
)

fun Task.toEntity() = TaskEntity(
    id = id,
    listId = listId,
    listColor = listColor,
    title = title,
    description = note,
    taskDate = taskDate,
    taskTime = taskTime,
    alarmTime = alarmTime,
    priorityCode = priority?.ordinal,
    isActive = isActive,
    isFavorite = isFavorite,
)

fun UserEntity.toDomain() = User(
    id = id,
    name = name,
    photoUri = photoUri
)

fun User.toEntity() = UserEntity(
    id = id,
    name = name,
    photoUri = photoUri
)