package com.app.memorista.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.memorista.models.TaskUI
import com.app.memorista.ui.adapters.TaskAdapter
import com.app.memorista.ui.viewmodels.ThisWeekTasksViewModel
import com.app.memorista.utils.addRepeatedJob
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.format.DateTimeFormatter

class ThisWeekTasksFragment : TaskListFragment() {

    private val mViewModel by viewModel<ThisWeekTasksViewModel>()

    private val mCompletedTasksAdapter by lazy {
        TaskAdapter(
            onStatusChecked = sharedViewModel::changeTaskStatus,
            onFavoriteStatusChecked = sharedViewModel::changeTaskFavoriteStatus
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.addRepeatedJob(Lifecycle.State.STARTED) {
            launch { mViewModel.tasksFlow.collect { handleTasksFlow(it) } }
            launch { mViewModel.listsFlow.collect { handleListsFlow(it) } }
            launch { mViewModel.completedTasksFlow.collect { handleCompletedTasksFlow(it) } }
        }
        setupUI()
        showCompletedCategories()
    }

    private fun setupUI() = binding?.let {
        it.rvCompletedTasks.layoutManager = LinearLayoutManager(requireContext())
        it.rvCompletedTasks.adapter = mCompletedTasksAdapter
        it.taskListHeader = "This week"
    }

    private fun handleCompletedTasksFlow(tasks: List<TaskUI>) {
        mCompletedTasksAdapter.submitList(tasks)
    }
}