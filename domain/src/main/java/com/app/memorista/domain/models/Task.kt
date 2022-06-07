package com.app.memorista.domain.models

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class Task(
    val id: Long,
    val title: String,
    val note: String,
    val listId: Long,
    val taskDate: LocalDate? = null,
    val taskTime: LocalTime? = null,
    val alarmTime: LocalTime? = null,
    val isActive: Boolean = false
)
