package com.app.memorista.domain.models.params

import java.time.LocalDateTime

data class NewTaskParams(
    val title: String?,
    val notes: String?,
    val listId: Long?,
    /*val taskDateTime: LocalDateTime?,
    val alarmDateTime: LocalDateTime? = null*/
)