package com.app.memorista.ui.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
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
import com.app.memorista.ui.viewmodels.HomeViewModel
import com.app.memorista.ui.viewmodels.SharedViewModel
import com.app.memorista.utils.addRepeatedJob
import com.app.memorista.utils.models.Resource
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.time.LocalTime
import kotlin.properties.Delegates

/**
 * @ClassName: MainFragment
 * @Description: class description
 * @Author: Arthur Galoyan
 * @Date: 3/9/2022 11:14 PM
 */
class HomeFragment : Fragment(), TasksListAdapter.OnItemClickListener {

    private var mBinding: FragmentHomeBinding? = null
    private val mViewModel by viewModel<HomeViewModel>()
    private val mSharedViewModel by sharedViewModel<SharedViewModel>()

    private var mTaskAdapter: TaskAdapter = TaskAdapter { }
    private var mTaskListAdapter: TasksListAdapter = TasksListAdapter(this)

    private var mHorizontalInset by Delegates.notNull<Int>()

    private var mTasks = listOf(
        TaskUI(1, "My task", "", LocalTime.now(), LocalDate.now(), LocalTime.now(), false, 1),
        TaskUI(2, "My task", "", LocalTime.now(), LocalDate.now(), LocalTime.now(), false, 1),
        TaskUI(3, "My task", "", LocalTime.now(), LocalDate.now(), LocalTime.now(), false, 1),
    )

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
            launch { mTaskAdapter.submitList(mTasks) }
        }
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        setupUI()
        setupToolbar()
    }

    private fun setupUI() {
        setupFab()
        setListsStartPadding()
    }

    private fun setupFab() {
        MainActivity.fab?.let {
            it.setOnClickListener { findNavController().navigate(R.id.action_homeFragment_to_createComponentDialogFragment) }
            it.show()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupToolbar() {
        val toolbar = (requireActivity() as MainActivity).mBinding?.toolbar
        toolbar?.root?.visibility = View.VISIBLE
        requireActivity().window.clearFlags(MainActivity.NO_LIMITS_FLAG)
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
                is Resource.Empty -> TODO()
            }
        }
    }

    override fun onItemClick(item: TaskListUI) {
        mSharedViewModel.selectedList = item
        SingleListBottomSheetDialog().show(parentFragmentManager, SingleListBottomSheetDialog.TAG)
    }
}