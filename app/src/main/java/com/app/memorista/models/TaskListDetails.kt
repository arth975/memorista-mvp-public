package com.app.memorista.models

import java.time.LocalDate

data class TaskListDetails(
    val categoryId: Long,
    val date: LocalDate
)