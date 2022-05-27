package com.app.toalarm.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.DatePicker
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.toalarm.R
import com.app.toalarm.databinding.FragmentHomeBinding
import com.app.toalarm.models.CategoryUI
import com.app.toalarm.ui.adapters.CategoriesSpinnerAdapter
import com.app.toalarm.ui.adapters.TaskAdapter
import com.app.toalarm.ui.viewmodels.HomeViewModel
import com.app.toalarm.utils.Resource
import com.app.toalarm.utils.addRepeatedJob
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate

/**
 * @ClassName: MainFragment
 * @Description: class description
 * @Author: Arthur Galoyan
 * @Date: 3/9/2022 11:14 PM
 */
class HomeFragment : BaseCalendarDialogFragment() {

    companion object {
        private const val IS_SAVED_STATE_KEY = "IS_SAVED_STATE"
    }

    private lateinit var mCategoriesFailedMessage: String

    private var mBinding: FragmentHomeBinding? = null
    private val mHomeViewModel by viewModel<HomeViewModel>()

    private var mTaskListAdapter: TaskAdapter = TaskAdapter { }
    private var mCategories = emptyList<CategoryUI>()

    private var isSavedInstance = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mBinding == null) {
            mBinding = FragmentHomeBinding.inflate(inflater)
            initResources()
            setupUI()
        }

        isSavedInstance = mHomeViewModel.isRestored
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        handleFlows()
    }

    override fun onStop() {
        super.onStop()
        mHomeViewModel.isRestored = true
    }

    private fun initResources() {
        mCategoriesFailedMessage = resources.getString(R.string.categories_failed_message)
    }

    private fun setupUI() {
        mBinding?.apply {
            selectedDate = LocalDate.now()
            fabAddNewTask.setOnClickListener { onFabClick() }
            btnCreateCategory
                .setOnClickListener { findNavController().navigate(R.id.action_mainFragment_to_createCategoryDialogFragment) }
            btnShowCalendarPickerDialog.setOnClickListener { showCalendarPickerDialog() }
        }
        setupTasksRecyclerView()
        setupTabLayout()
    }

    private fun setupTasksRecyclerView() {
        mBinding?.rvTasks?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mTaskListAdapter
        }
    }

    private fun setupTabLayout() {
        mBinding?.tlTaskStatus?.apply {
            val titles =
                resources.getStringArray(R.array.task_status_tab_titles).toCollection(ArrayList())
            var tab: TabLayout.Tab
            for (title in titles) {
                tab = newTab().apply { text = title }
                addTab(tab)
            }
        }
    }

    private fun setupCategoriesSpinner(categories: List<CategoryUI>) {
        mBinding?.spinnerCategories?.apply {
            adapter = CategoriesSpinnerAdapter(requireContext(), categories)
            onItemSelectedListener = OnCategoryItemSelectedListener()
        }
    }

    private fun onFabClick() {
        findNavController().navigate(R.id.action_mainFragment_to_addNewTaskFragment)
    }

    private fun handleFlows() {
        viewLifecycleOwner.addRepeatedJob(Lifecycle.State.STARTED) {
            launch { handleCategoriesFlow() }
            launch { handleTasksFlow() }
        }
    }

    private suspend fun handleCategoriesFlow() {
        mHomeViewModel.categoriesFlow.collectLatest { resource ->
            if (!isSavedInstance) {
                when (resource) {
                    is Resource.Success -> {
                        mCategories = resource.data
                        setupCategoriesSpinner(mCategories)
                    }
                    is Resource.Error ->
                        Toast.makeText(requireContext(), mCategoriesFailedMessage, Toast.LENGTH_LONG)
                            .show()
                    is Resource.Loading -> {}
                }
            } else {
                isSavedInstance = false
            }
        }
    }

    private suspend fun handleTasksFlow() {
        mHomeViewModel.tasksFlow.collectLatest { resource ->
            when (resource) {
                is Resource.Success ->
                    mTaskListAdapter.submitList(resource.data)
                is Resource.Error ->
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_LONG).show()
                is Resource.Loading -> {}
            }
        }
    }

    private inner class OnCategoryItemSelectedListener : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            mHomeViewModel.selectCategory(mCategories[position].id)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val date = LocalDate.of(year, month, dayOfMonth)
        mBinding?.selectedDate = date
        mHomeViewModel.selectDate(date)
    }
}