package com.app.memorista.domain.models.params

import java.time.LocalDate

data class TaskParams(
    val listId: Long,
    val date: LocalDate = LocalDate.now()
)
