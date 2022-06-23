package com.app.memorista.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.memorista.models.TaskUI
import com.app.memorista.ui.adapters.TaskAdapter
import com.app.memorista.ui.viewmodels.TodayTasksViewModel
import com.app.memorista.utils.addRepeatedJob
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * @ClassName: MainFragment
 * @Description: class description
 * @Author: Arthur Galoyan
 * @Date: 3/9/2022 11:14 PM
 */
class TodayTasksFragment : TaskListFragment() {

    private val mViewModel by viewModel<TodayTasksViewModel>()
    private val mDateFormatter = DateTimeFormatter.ofPattern("dd MMMM")

    private val mCompletedTasksAdapter by lazy {
        TaskAdapter(
            onItemClick = ::onTaskItemClick,
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
        it.taskListHeader = "Today, " + mDateFormatter.format(LocalDate.now())
        it.rvCompletedTasks.layoutManager = LinearLayoutManager(requireContext())
        it.rvCompletedTasks.adapter = mCompletedTasksAdapter
    }

    private fun handleCompletedTasksFlow(tasks: List<TaskUI>) {
        mCompletedTasksAdapter.submitList(tasks)
    }
}