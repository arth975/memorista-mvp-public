package com.app.memorista.data.mappers

import com.app.memorista.data.local.entities.ListEntity
import com.app.memorista.data.local.entities.TaskEntity
import com.app.memorista.data.local.entities.UserEntity
import com.app.memorista.domain.models.Task
import com.app.memorista.domain.models.TaskList
import com.app.memorista.domain.models.User

fun ListEntity.toDomain() = TaskList(
    id = id,
    name = name,
    tasksCount = tasksCount,
    completedTaskPercent = completedTasksPercent,
    color = color
)

fun TaskList.toEntity() = ListEntity(
    id = id,
    name = name,
    tasksCount = tasksCount,
    completedTasksPercent = completedTaskPercent,
    color = color
)

fun TaskEntity.toDomain() = Task(
    title = title,
    note = description,
    taskDate = taskDate,
    taskTime = taskTime,
    listId = listId,
    alarmTime = alarmTime,
    isActive = isActive,
    id = id
)

fun Task.toEntity() = TaskEntity(
    title = title,
    description = note,
    listId = listId,
    taskDate = taskDate,
    taskTime = taskTime,
    alarmTime = alarmTime,
    isActive = isActive,
    id = id
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