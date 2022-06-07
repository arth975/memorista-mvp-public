package com.app.memorista.utils.models

import com.app.memorista.models.TaskListUI

sealed class TaskListEvent(val data: TaskListUI?) {

    companion object {
        fun nameChanged(data: TaskListUI?) = NameChanged(data)
        fun colorChanged(data: TaskListUI?) = ColorChanged(data)
        fun initial(data: TaskListUI?) = Initial(data)
    }

    class NameChanged(data: TaskListUI?) : TaskListEvent(data)
    class ColorChanged(data: TaskListUI?) : TaskListEvent(data)
    class Initial(data: TaskListUI?) : TaskListEvent(data)
}
