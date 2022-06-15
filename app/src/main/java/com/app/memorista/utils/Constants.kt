package com.app.memorista.utils

import android.graphics.Color
import com.app.memorista.domain.models.TaskList

val NEW_TASK_LIST = TaskList(
    name = "New List",
    tasksCount = 0,
    completedTaskCount = 0,
    color = Color.parseColor("#1C79F6"),
    id = 0
)