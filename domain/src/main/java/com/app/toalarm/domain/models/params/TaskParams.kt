package com.app.toalarm.domain.models.params

import java.time.LocalDate

data class TaskParams(
    val categoryId: Long,
    val taskDate: LocalDate
)
