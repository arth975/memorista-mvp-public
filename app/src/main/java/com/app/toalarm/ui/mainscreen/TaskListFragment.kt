package com.app.toalarm.ui.mainscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.toalarm.R
import com.app.toalarm.data.model.Task
import com.app.toalarm.databinding.FragmentTaskListBinding
import com.app.toalarm.ui.MainActivity
import com.app.toalarm.ui.mainscreen.adapters.TaskAdapter
import java.time.LocalDateTime

/**
 * @ClassName: TaskListFragment
 * @Description: class description
 * @Author: Arthur Galoyan
 * @Date: 3/10/2022 11:31 PM
 */
class TaskListFragment : Fragment() {

    companion object {
        fun newInstance(): TaskListFragment = TaskListFragment()
    }

    private lateinit var mBinding: FragmentTaskListBinding
    private var isInitial = true

    private val taskItems = arrayListOf(
        Task("Meeting:  UX Case", "Discuss Milton website", LocalDateTime.now()),
        Task("Meeting:  UX Case", "Discuss Milton website", LocalDateTime.now()),
        Task("Meeting:  UX Case", "Discuss Milton website", LocalDateTime.now()),
        Task("Meeting:  UX Case", "Discuss Milton website", LocalDateTime.now()),
        Task("Meeting:  UX Case", "Discuss Milton website", LocalDateTime.now()),
        Task("Meeting:  UX Case", "Discuss Milton website", LocalDateTime.now()),
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentTaskListBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onResume() {
        super.onResume()
        if (isInitial) {
            setupTasksList(taskItems)
            isInitial = false
        }
    }

    private fun setupTasksList(tasks: ArrayList<Task>) {
        with(mBinding.rvTasks) {
            adapter = TaskAdapter(tasks, ::onTaskItemClick)
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun onTaskItemClick(task: Task?) {
        try {
            (requireActivity() as MainActivity).showBackButton()
            findNavController().navigate(R.id.action_mainFragment_to_addNewTaskFragment)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}