package com.app.toalarm.data.mappers

import com.app.toalarm.data.local.entities.CategoryEntity
import com.app.toalarm.data.local.entities.TaskEntity
import com.app.toalarm.domain.models.Category
import com.app.toalarm.domain.models.Task
import java.time.LocalDateTime

fun CategoryEntity.toDomain() = Category(
    id = this.id,
    name = this.name
)

fun Category.toEntity() = CategoryEntity(
    name = name,
    id = id,
)

fun TaskEntity.toDomain() = Task(
    title = title,
    note = note,
    taskDateTime = LocalDateTime.of(taskDate, taskTime),
    categoryId = categoryId,
    alarmDateTime = alarmDateTime,
    isActive = isActive,
    id = id
)

fun Task.toEntity() = TaskEntity(
    title = title,
    note = note,
    categoryId = categoryId,
    taskDate = taskDateTime.toLocalDate(),
    taskTime = taskDateTime.toLocalTime(),
    alarmDateTime = this.alarmDateTime,
    isActive = this.isActive,
    id = id
)