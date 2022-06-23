package com.app.memorista.domain.models.params

import java.time.LocalDate

data class TaskByDateParams(
    val fromDate: LocalDate,
    val toDate: LocalDate = fromDate
)
