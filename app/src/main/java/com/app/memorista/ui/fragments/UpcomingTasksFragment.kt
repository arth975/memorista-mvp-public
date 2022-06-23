package com.app.memorista.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import com.app.memorista.ui.viewmodels.UpcomingTasksViewModel
import com.app.memorista.utils.addRepeatedJob
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class UpcomingTasksFragment : TaskListFragment() {

    private val mViewModel by viewModel<UpcomingTasksViewModel>()
    private val mDateFormatter = DateTimeFormatter.ofPattern("dd MMM")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.addRepeatedJob(Lifecycle.State.STARTED) {
            launch { mViewModel.tasksFlow.collect { handleTasksFlow(it) } }
            launch { mViewModel.listsFlow.collect { handleListsFlow(it) } }
        }

        binding?.taskListHeader = "Upcoming"
    }
}