package com.app.memorista.ui.fragments

import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.memorista.R
import com.app.memorista.databinding.FragmentHomeBinding
import com.app.memorista.models.TaskListUI
import com.app.memorista.models.TaskUI
import com.app.memorista.ui.MainActivity
import com.app.memorista.ui.adapters.TaskAdapter
import com.app.memorista.ui.adapters.TasksListAdapter
import com.app.memorista.ui.dialogs.SingleListBottomSheetDialog
import com.app.memorista.ui.dialogs.SortBottomSheetDialog
import com.app.memorista.ui.viewmodels.HomeViewModel
import com.app.memorista.ui.viewmodels.SharedViewModel
import com.app.memorista.utils.AnimationContent
import com.app.memorista.utils.addRepeatedJob
import com.app.memorista.utils.models.Resource
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.properties.Delegates

/**
 * @ClassName: MainFragment
 * @Description: class description
 * @Author: Arthur Galoyan
 * @Date: 3/9/2022 11:14 PM
 */
class HomeFragment : Fragment(), TaskAdapter.OnCheckedChangeListener,
    TasksListAdapter.OnItemClickListener, TasksListAdapter.OnNewItemClickListener {

    private var mBinding: FragmentHomeBinding? = null
    private val mViewModel by viewModel<HomeViewModel>()
    private val mSharedViewModel by sharedViewModel<SharedViewModel>()

    private var mTaskAdapter: TaskAdapter = TaskAdapter(this)
    private var mTaskListAdapter: TasksListAdapter = TasksListAdapter(this, null, this)

    private var mHorizontalInset by Delegates.notNull<Int>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mBinding == null) {
            mBinding = FragmentHomeBinding.inflate(inflater)
            mHorizontalInset = getHorizontalInset()
            initLists()
        }
        viewLifecycleOwner.addRepeatedJob(Lifecycle.State.STARTED) {
            launch { handleListsFlow() }
            launch { handleTasksFlow() }
        }

        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        setupUI()
    }

    private fun setupUI() {
        setListsStartPadding()
        mBinding?.buttonSort?.setOnClickListener {
            SortBottomSheetDialog().show(
                parentFragmentManager,
                "SORT"
            )
        }
    }

    private fun initLists() {
        val horizontalLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        initList(mBinding?.rvLists, mTaskListAdapter, horizontalLayoutManager)
        initList(mBinding?.rvTodayTasks, mTaskAdapter)
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
        mBinding?.rvLists?.setPadding(mHorizontalInset, 0, 0, 0)
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

    private suspend fun handleListsFlow() {
        mViewModel.listsFlow.collect { resource ->
            when (resource) {
                is Resource.Success ->
                    mTaskListAdapter.submitList(resource.data)
                is Resource.Error ->
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
                is Resource.Loading -> {}
                is Resource.Empty -> {}
            }
        }
    }

    private suspend fun handleTasksFlow() {
        mViewModel.tasksFlow.collect { tasks ->
            mTaskAdapter.submitList(tasks)
        }
    }

    override fun onItemClick(item: TaskListUI) {
        mSharedViewModel.selectedList = item
        SingleListBottomSheetDialog().show(parentFragmentManager, SingleListBottomSheetDialog.TAG)
    }

    override fun onCheckedChange(item: TaskUI, isChecked: Boolean) {
        mViewModel.changeTaskActivity(item, isChecked)
    }

    override fun onItemClick() {
        mSharedViewModel.selectedList = null
        SingleListBottomSheetDialog().show(parentFragmentManager, "TASK-LIST")
    }
}