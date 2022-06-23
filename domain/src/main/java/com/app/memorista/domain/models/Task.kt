package com.app.memorista.domain.models

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class Task(
    val id: Long,
    val listId: Long,
    val listColor: Int,
    val title: String,
    val note: String,
    val taskDate: LocalDate? = null,
    val taskTime: LocalTime? = null,
    val alarmTime: LocalTime? = null,
    val priority: Priority? = null,
    val isActive: Boolean = false,
    val isFavorite: Boolean = false
)
