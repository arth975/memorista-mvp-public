package com.app.memorista.utils

import android.graphics.Color
import androidx.core.content.ContextCompat
import com.app.memorista.R
import com.app.memorista.domain.models.TaskList

val NEW_TASK_LIST = TaskList(
    name = "New List",
    tasksCount = 0,
    completedTaskPercent = 0,
    color = Color.parseColor("#1C79F6"),
    id = 0
)