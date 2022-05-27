package com.app.toalarm.domain.models.params

import java.time.LocalDateTime

data class NewTaskParams(
    val title: String?,
    val notes: String?,
    val categoryId: Long?,
    val taskDateTime: LocalDateTime?,
    val alarmDateTime: LocalDateTime? = null
)