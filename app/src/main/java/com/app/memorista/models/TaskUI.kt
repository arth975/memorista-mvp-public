package com.app.memorista.models

import java.time.LocalDate
import java.time.LocalTime

data class TaskUI(
    val id: Long,
    val listId: Long,
    val listColor: Int,
    val title: String,
    val description: String,
    val taskTime: LocalTime?,
    val taskDate: LocalDate?,
    val alarmTime: LocalTime?,
    val priority: PriorityUI?,
    val isActive: Boolean,
    val isFavorite: Boolean = false
)
