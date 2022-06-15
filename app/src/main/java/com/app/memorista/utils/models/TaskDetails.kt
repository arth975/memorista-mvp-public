package com.app.memorista.utils.models

import com.app.memorista.models.PriorityUI
import com.app.memorista.models.TaskListUI
import java.time.LocalDate
import java.time.LocalTime

data class TaskDetails(
    val event: TaskDetailsEvent = TaskDetailsEvent.Initial,
    val list: TaskListUI? = null,
    val date: LocalDate? = LocalDate.now(),
    val time: LocalTime? = null,
    val isActive: Boolean = false,
    val titleText: String? = null,
    val descriptionText: String? = null,
    val priority: PriorityUI? = null
)
