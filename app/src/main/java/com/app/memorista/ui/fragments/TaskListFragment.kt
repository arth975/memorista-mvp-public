package com.app.memorista.ui.fragments

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.memorista.R
import com.app.memorista.databinding.FragmentTasksListBinding
import com.app.memorista.models.TaskListUI
import com.app.memorista.models.TaskUI
import com.app.memorista.ui.adapters.TaskAdapter
import com.app.memorista.ui.adapters.TasksListAdapter
import com.app.memorista.ui.bottom_sheets.SingleListBottomSheet
import com.app.memorista.ui.bottom_sheets.SingleTaskBottomSheet
import com.app.memorista.ui.bottom_sheets.SortBottomSheet
import com.app.memorista.ui.viewmodels.SharedViewModel
import com.app.memorista.ui.viewmodels.TaskListViewModel
import com.app.memorista.utils.addRepeatedJob
import com.app.memorista.utils.models.Resource
import com.app.memorista.utils.models.TaskAction
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

abstract class TaskListFragment : Fragment(), TasksListAdapter.OnItemClickListener,
    TasksListAdapter.OnCreateNewItemClickListener, PopupMenu.OnMenuItemClickListener {

    protected var binding: FragmentTasksListBinding? = null
    protected val sharedViewModel by sharedViewModel<SharedViewModel>()
    private val mViewModel by viewModel<TaskListViewModel>()

    private lateinit var mSelectedTask: TaskUI

    private val taskAdapter by lazy {
        TaskAdapter(
            onItemClick = ::onTaskItemClick,
            onLongClick = ::onTaskItemLongClick,
            onStatusChecked = sharedViewModel::changeTaskStatus,
            onFavoriteStatusChecked = sharedViewModel::changeTaskFavoriteStatus
        )
    }
    private val taskListAdapter by lazy { TasksListAdapter(this, null, this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null) {
            binding = FragmentTasksListBinding.inflate(inflater)
            setupUI()
        }

        viewLifecycleOwner.addRepeatedJob(Lifecycle.State.STARTED) {
            launch { handleActionFlow() }
        }

        return binding?.root
    }

    private fun setupUI() {
        setListsStartPadding()
        initLists()
        binding?.buttonSort
            ?.setOnClickListener { SortBottomSheet().show(parentFragmentManager, "SORT") }
    }

    private fun initLists() {
        val horizontalLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        initList(binding?.rvLists, taskListAdapter, horizontalLayoutManager)
        initList(binding?.rvTasks, taskAdapter)
    }

    private fun initList(
        rv: RecyclerView?,
        adapter: ListAdapter<*, *>,
        layoutManager: LinearLayoutManager = LinearLayoutManager(requireContext())
    ) {
        rv?.apply {
            this.adapter = adapter
            this.layoutManager = layoutManager
        }
    }

    private fun setListsStartPadding() {
        binding?.rvLists?.setPadding(getHorizontalInset(), 0, 0, 0)
    }

    private fun getHorizontalInset():
            Int = (getScreenWidth() * getHorizontalInsetPercent()).toInt()

    private fun getHorizontalInsetPercent(): Float {
        val value = TypedValue()
        resources.getValue(R.dimen.guide_percent_main_start, value, true)
        return value.float
    }

    private fun getScreenWidth(): Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        requireActivity().windowManager.currentWindowMetrics.bounds.width()
    } else {
        val metrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(metrics)
        metrics.widthPixels
    }

    protected fun handleListsFlow(resource: Resource<List<TaskListUI>>) {
        when (resource) {
            is Resource.Success ->
                taskListAdapter.submitList(resource.data)
            is Resource.Error ->
                Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
            is Resource.Loading -> {}
            is Resource.Empty -> {}
        }
    }

    protected fun handleTasksFlow(tasks: List<TaskUI>) {
        taskAdapter.submitList(tasks)
    }

    private suspend fun handleActionFlow() {
        mViewModel.actionFlow.collect { action ->
            when(action) {
                is TaskAction.DeleteAction ->
                    Toast.makeText(requireContext(), "The task was successfully deleted!", Toast.LENGTH_SHORT)
                        .show()
                is TaskAction.Failure ->
                    Toast.makeText(requireContext(), action.message, Toast.LENGTH_SHORT)
                        .show()
            }
        }
    }

    protected fun showCompletedCategories() = binding?.let {
        it.textCompletedTasksTitle.visibility = View.VISIBLE
        it.dividerCompletedTasks.visibility = View.VISIBLE
        it.rvCompletedTasks.visibility = View.VISIBLE
    }

    protected fun onTaskItemClick(task: TaskUI) {
        sharedViewModel.selectedTask = task
        SingleTaskBottomSheet().show(parentFragmentManager, "")
    }

    override fun onItemClick(item: TaskListUI) {
        sharedViewModel.selectedList = item
        SingleListBottomSheet().show(parentFragmentManager, SingleListBottomSheet.TAG)
    }

    override fun onCreateNewItemClick() {
        sharedViewModel.selectedList = null
        SingleListBottomSheet().show(parentFragmentManager, "TASK-LIST")
    }

    protected fun onTaskItemLongClick(view: View, item: TaskUI) {
        mSelectedTask = item
        PopupMenu(requireContext(), view, Gravity.END or Gravity.TOP).also {
            it.inflate(R.menu.menu_task_item_popup)
            it.setOnMenuItemClickListener(this)
            it.show()
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.taskPopupAddToFavorites -> {}
            R.id.taskPopupCategory -> {}
            R.id.taskPopupPriority -> {}
            R.id.taskPopupEdit ->
                openSingleTaskBottomSheet(mSelectedTask)
            R.id.taskPopupDelete ->
                showDeleteTaskDialog(mSelectedTask)
        }
        return true
    }

    private fun openSingleTaskBottomSheet(task: TaskUI) {
        sharedViewModel.selectedTask = task
        SingleTaskBottomSheet().show(parentFragmentManager, SingleTaskBottomSheet.TAG)
    }

    private fun showDeleteTaskDialog(task: TaskUI) {
        AlertDialog.Builder(requireContext())
            .setMessage(R.string.dialog_message_delete_task)
            .setNegativeButton(R.string.dialog_button_negative, null)
            .setPositiveButton(R.string.dialog_button_positive) { _, _ ->
                mViewModel.deleteTask(task)
            }
            .show()
    }
}