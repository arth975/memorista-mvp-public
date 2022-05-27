package com.app.toalarm.domain.models

import java.time.LocalDate
import java.time.LocalDateTime

data class Task(
    val title: String,
    val note: String,
    val taskDateTime: LocalDateTime,
    val categoryId: Long,
    val isActive: Boolean,
    val alarmDateTime: LocalDateTime? = null,
    val id: Long
)
