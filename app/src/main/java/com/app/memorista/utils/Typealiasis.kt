package com.app.memorista.utils

import android.view.View
import com.app.memorista.models.TaskUI

typealias OnCheckedChange = (TaskUI, Boolean) -> Unit
typealias OnTaskItemClick = (TaskUI) -> Unit
typealias OnTaskItemLongClick = (View, TaskUI) -> Unit