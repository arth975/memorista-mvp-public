package com.app.memorista.utils.models

sealed class TaskAction(val message: String? = null) {

    object ChangeCategoryAction : TaskAction()
    object DeleteAction : TaskAction()
    object Initial : TaskAction()
    class Failure(message: String?) : TaskAction(message)
}