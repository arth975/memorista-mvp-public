package com.app.memorista.ui.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.memorista.R
import com.app.memorista.databinding.FragmentCreateTaskBinding
import com.app.memorista.databinding.LayoutCreateTaskToolboxBinding
import com.app.memorista.ui.adapters.TaskListItemDetailsLookup
import com.app.memorista.ui.adapters.TaskListItemKeyProvider
import com.app.memorista.ui.adapters.TasksListAdapter
import com.app.memorista.ui.viewmodels.CreateTaskViewModel
import com.app.memorista.utils.addRepeatedJob
import com.app.memorista.utils.models.Resource
import com.app.memorista.utils.models.TaskDetailsEvent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

/**
 * @ClassName: AddNewTaskFragment
 * @Description: class description
 * @Author: Arthur Galoyan
 * @Date: 3/9/2022 11:14 PM
 */
class CreateTaskFragment : Fragment(), TimePickerDialog.OnTimeSetListener,
    DatePickerDialog.OnDateSetListener, CompoundButton.OnCheckedChangeListener {

    private var mBinding: FragmentCreateTaskBinding? = null
    private var mToolboxMergeBinding: LayoutCreateTaskToolboxBinding? = null
    private val mViewModel by viewModel<CreateTaskViewModel>()

    private lateinit var mCategoriesFailedMessage: String
    private var tracker: SelectionTracker<Long>? = null

    private val mCurrentDate by lazy { LocalDate.now() }
    private val mTaskListAdapter by lazy { TasksListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mBinding == null) {
            mBinding = FragmentCreateTaskBinding.inflate(inflater)
            mToolboxMergeBinding = LayoutCreateTaskToolboxBinding.bind(mBinding!!.root)
            setupUI()
            initResources()
        }
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        handleFlows()
    }

    private fun setupUI() {
        mBinding?.apply {
            rvLists.adapter = mTaskListAdapter
            rvLists.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            checkBox.setOnCheckedChangeListener(this@CreateTaskFragment)
        }
        mToolboxMergeBinding?.apply {
            buttonCalendar.setOnClickListener { showDatePickerDialog() }
            buttonTime.setOnClickListener { showTimePickerDialog() }
        }
        setupTasksListSelection()
    }

    private fun setupTasksListSelection() {
        mBinding?.rvLists?.let {
            tracker = SelectionTracker.Builder(
                "list-selection", it,
                TaskListItemKeyProvider(mTaskListAdapter),
                TaskListItemDetailsLookup(it),
                StorageStrategy.createLongStorage()
            ).withSelectionPredicate(
                SelectionPredicates.createSelectAnything()
            ).build()

            tracker?.addObserver(object : SelectionTracker.SelectionObserver<Long>() {
                override fun onSelectionChanged() {
                    super.onSelectionChanged()
                    /*Toast.makeText(
                        requireContext(),
                        mViewModel.listsFlow.value.data.toString(),
                        Toast.LENGTH_SHORT
                    ).show()*/
                }
            })

            mTaskListAdapter.tracker = tracker
        }
    }

    private fun initResources() {
        mCategoriesFailedMessage = resources.getString(R.string.categories_failed_message)
    }

    private fun handleFlows() {
        viewLifecycleOwner.addRepeatedJob(Lifecycle.State.STARTED) {
            launch { handleListsFlow() }
            launch { handleEventsFlow() }
            launch { handleValidationFlow() }
        }
    }

    private suspend fun handleListsFlow() {
        mViewModel.listsFlow.collectLatest { resource ->
            when (resource) {
                is Resource.Success ->
                    mTaskListAdapter.submitList(resource.data)
                is Resource.Error ->
                    Toast.makeText(requireContext(), mCategoriesFailedMessage, Toast.LENGTH_LONG)
                        .show()
                is Resource.Loading -> {}
            }
        }
    }

    private suspend fun handleEventsFlow() {
        mViewModel.taskDetailsFlow.collect { details ->
            when (details.event) {
                TaskDetailsEvent.DateChanged -> {
                    if (details.date != null) {
                        mBinding?.textDate?.visibility = View.VISIBLE
                        mBinding?.textDate?.text =
                            details.date.format(DateTimeFormatter.ISO_LOCAL_DATE)
                    } else {
                        mBinding?.textDate?.visibility = View.GONE
                        mBinding?.textDate?.text = null
                    }
                }
                TaskDetailsEvent.TimeChanged -> {
                    if (details.time != null) {
                        mBinding?.textTime?.visibility = View.VISIBLE
                        mBinding?.textTime?.text =
                            details.time.format(DateTimeFormatter.ISO_TIME)
                    } else {
                        mBinding?.textTime?.visibility = View.GONE
                        mBinding?.textTime?.text = null
                    }
                }
                TaskDetailsEvent.ListChanged -> {
                    mToolboxMergeBinding?.textSelectedListName?.text = details.list?.name
                }
                TaskDetailsEvent.Initial -> {
                    mBinding?.textTime?.visibility = View.GONE
                    mBinding?.textDate?.visibility = View.GONE
                }
            }
        }
    }

    private suspend fun handleValidationFlow() {
        mViewModel.validationSuccessFlow.collectLatest { validation ->
            if (validation.isValid)
                findNavController().navigateUp()
            else
                Toast.makeText(requireContext(), "Invalid input data.", Toast.LENGTH_LONG)
                    .show()
        }
    }

    private fun showDatePickerDialog() {
        DatePickerDialog(
            requireContext(),
            this,
            mCurrentDate.year,
            mCurrentDate.monthValue,
            mCurrentDate.dayOfMonth
        ).show()
    }

    private fun showTimePickerDialog() {
        val currentTime = LocalTime.now()
        TimePickerDialog(
            requireContext(),
            this,
            currentTime.hour,
            currentTime.minute,
            true
        ).show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        mViewModel.selectTime(LocalTime.of(hourOfDay, minute))
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        mViewModel.selectDate(LocalDate.of(year, month, dayOfMonth))
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        mViewModel.setActivity(isChecked)
    }

}