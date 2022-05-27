package com.app.toalarm.mappers

import com.app.toalarm.domain.models.Category
import com.app.toalarm.domain.models.Task
import com.app.toalarm.models.CategoryUI
import com.app.toalarm.models.TaskUI

fun Category.toUI() = CategoryUI(
    name = this.name,
    id = this.id
)

fun Task.toUI() = TaskUI(
    title = title,
    description = note,
    taskDateTime = taskDateTime,
    alarmDateTime = alarmDateTime ?: taskDateTime,
    isActive = isActive,
    categoryId = categoryId,
    id = id
)

fun TaskUI.toDomain() = Task(
    title = title,
    note = description,
    taskDateTime = taskDateTime,
    categoryId = categoryId,
    isActive = isActive,
    alarmDateTime = alarmDateTime,
    id = id
)