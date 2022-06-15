package com.app.memorista.utils.models

sealed class TaskDetailsEvent {

    object DateChanged : TaskDetailsEvent()
    object TimeChanged : TaskDetailsEvent()
    object ListChanged : TaskDetailsEvent()
    object ActivityChanged : TaskDetailsEvent()
    object PriorityChanged : TaskDetailsEvent()
    object Initial : TaskDetailsEvent()
}
