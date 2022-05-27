package com.app.toalarm.ui.fragments

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.app.toalarm.R
import com.app.toalarm.databinding.FragmentCreateTaskBinding
import com.app.toalarm.models.CategoryUI
import com.app.toalarm.ui.adapters.CategoriesSpinnerAdapter
import com.app.toalarm.ui.viewmodels.CreateTaskViewModel
import com.app.toalarm.utils.Resource
import com.app.toalarm.utils.addRepeatedJob
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

/**
 * @ClassName: AddNewTaskFragment
 * @Description: class description
 * @Author: Arthur Galoyan
 * @Date: 3/9/2022 11:14 PM
 */
class CreateTaskFragment : BaseCalendarDialogFragment(), TimePickerDialog.OnTimeSetListener {

    private var mBinding: FragmentCreateTaskBinding? = null
    private val mCreateTaskViewModel by viewModel<CreateTaskViewModel>()

    private lateinit var mCategoriesFailedMessage: String
    private var mCategories = emptyList<CategoryUI>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mBinding == null) {
            mBinding = FragmentCreateTaskBinding.inflate(inflater)
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
            fabCreateTask.setOnClickListener { createTask() }
            btnShowTimePickerDialog.setOnClickListener { showTimePickerDialog() }
            btnShowCalendarPickerDialog.setOnClickListener { showCalendarPickerDialog() }
            tvTaskDate.text = mSelectedTaskDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE)
            tvTaskAlarmTime.text = mSelectedTaskDateTime.format(DateTimeFormatter.ISO_LOCAL_TIME)
        }
    }

    private fun initResources() {
        mCategoriesFailedMessage = resources.getString(R.string.categories_failed_message)
    }

    private fun handleFlows() {
        viewLifecycleOwner.addRepeatedJob(Lifecycle.State.STARTED) {
            launch { handleCategoriesFlow() }
            launch { handleValidationFlow() }
        }
    }

    private suspend fun handleCategoriesFlow() {
        mCreateTaskViewModel.categoriesFlow.collectLatest { resource ->
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
        }
    }

    private suspend fun handleValidationFlow() {
        mCreateTaskViewModel.validationSuccessFlow.collectLatest { validation ->
            if (validation.isValid)
                findNavController().navigateUp()
            else
                Toast.makeText(requireContext(), "Invalid input data.", Toast.LENGTH_LONG)
                    .show()
        }
    }

    private fun setupCategoriesSpinner(categories: List<CategoryUI>) {
        mBinding?.spinnerCategories?.apply {
            adapter = CategoriesSpinnerAdapter(requireContext(), categories)
        }
    }

    private fun createTask() {
        mBinding?.apply {
            mCreateTaskViewModel.createTask(
                title = etTaskTitle.text?.toString(),
                notes = etTaskNotes.text?.toString(),
                categoryId = mCategories[spinnerCategories.selectedItemPosition].id,
                taskDateTime = mSelectedTaskDateTime
            )
        }
    }

    private fun showTimePickerDialog() {
        TimePickerDialog(
            requireContext(),
            this,
            mSelectedTaskDateTime.hour,
            mSelectedTaskDateTime.minute,
            true
        ).show()
    }

    private fun updateSelectedDateTime(
        date: LocalDate = mSelectedTaskDateTime.toLocalDate(),
        time: LocalTime = mSelectedTaskDateTime.toLocalTime()
    ) {
        mSelectedTaskDateTime = LocalDateTime.of(date, time)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        updateSelectedDateTime(date = LocalDate.of(year, month, dayOfMonth))
        mBinding?.tvTaskDate?.text = mSelectedTaskDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        updateSelectedDateTime(time = LocalTime.of(hourOfDay, minute))
        mBinding?.tvTaskAlarmTime?.text = mSelectedTaskDateTime.format(DateTimeFormatter.ISO_TIME)
    }

}