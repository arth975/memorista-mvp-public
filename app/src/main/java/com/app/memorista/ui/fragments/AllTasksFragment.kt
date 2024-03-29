package com.app.memorista.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import com.app.memorista.models.TaskListUI
import com.app.memorista.models.TaskUI
import com.app.memorista.ui.viewmodels.AllTasksViewModel
import com.app.memorista.utils.addRepeatedJob
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AllTasksFragment : TaskListFragment() {

    private val mViewModel by viewModel<AllTasksViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.addRepeatedJob(Lifecycle.State.STARTED) {
            launch { mViewModel.tasksFlow.collect { handleTasksFlow(it) } }
            launch { mViewModel.listsFlow.collect { handleListsFlow(it) } }
        }
    }
}