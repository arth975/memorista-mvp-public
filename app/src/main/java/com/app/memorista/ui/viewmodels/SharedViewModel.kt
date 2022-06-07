package com.app.memorista.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.app.memorista.models.TaskListUI

class SharedViewModel : ViewModel() {

    var selectedList: TaskListUI? = null
}