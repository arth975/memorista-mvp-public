package com.app.toalarm.models

import java.time.LocalDateTime

data class TaskUI(
    val title: String,
    val description: String,
    val taskDateTime: LocalDateTime,
    val alarmDateTime: LocalDateTime,
    val isActive: Boolean,
    val categoryId: Long,
    val id: Long
)
