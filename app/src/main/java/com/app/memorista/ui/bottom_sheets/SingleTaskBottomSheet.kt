package com.app.memorista.ui.bottom_sheets

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.memorista.R
import com.app.memorista.databinding.FragmentCreateTaskBinding
import com.app.memorista.models.PriorityUI
import com.app.memorista.models.TaskListUI
import com.app.memorista.ui.adapters.TaskListItemDetailsLookup
import com.app.memorista.ui.adapters.TaskListItemKeyProvider
import com.app.memorista.ui.adapters.TasksListAdapter
import com.app.memorista.ui.viewmodels.SharedViewModel
import com.app.memorista.ui.viewmodels.SingleTaskViewModel
import com.app.memorista.utils.DateTimeUtils
import com.app.memorista.utils.addRepeatedJob
import com.app.memorista.utils.models.Resource
import com.app.memorista.utils.models.TaskDetails
import com.app.memorista.utils.models.TaskDetailsEvent
import com.app.memorista.utils.showSoftKeyboard
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.time.LocalTime

/**
 * @ClassName: AddNewTaskFragment
 * @Description: class description
 * @Author: Arthur Galoyan
 * @Date: 3/9/2022 11:14 PM
 */
class SingleTaskBottomSheet : BottomSheetDialogFragment(), TimePickerDialog.OnTimeSetListener,
    DatePickerDialog.OnDateSetListener, CompoundButton.OnCheckedChangeListener,
    TasksListAdapter.OnItemSelectedListener, PopupMenu.OnMenuItemClickListener {

    companion object {
        const val TAG = "SingleTaskBottomSheet"
    }

    private var mBinding: FragmentCreateTaskBinding? = null
    private val mViewModel by viewModel<SingleTaskViewModel>()
    private val mSharedViewModel by sharedViewModel<SharedViewModel>()

    private lateinit var mCategoriesFailedMessage: String
    private lateinit var mPrioritySymbol: String
    private var tracker: SelectionTracker<Long>? = null

    private val mSelectedTask by lazy { mSharedViewModel.selectedTask }
    private val mTaskListAdapter by lazy { TasksListAdapter(onItemSelectedListener = this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mBinding == null) {
            mBinding = FragmentCreateTaskBinding.inflate(inflater)
            initResources()
            mBinding?.editTextNote?.showSoftKeyboard()
        }
        return mBinding?.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        if (dialog is BottomSheetDialog) {
            dialog.behavior.skipCollapsed = true
            dialog.behavior.state = STATE_EXPANDED
            dialog.behavior.isDraggable = false
            dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                dialog.window?.setDecorFitsSystemWindows(false)
//                mBinding?.root?.setOnApplyWindowInsetsListener { v, insets ->
//                    val typeMask = WindowInsets.Type.ime()
//                    val inset = insets.getInsets(typeMask)
//                    Log.d("WINDOW_INSETS", "Apply")
//                    v.updatePadding(bottom = inset.bottom)
//                    insets
//                }
//            } else {
//                dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
//            }
        }
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupUI()
        handleFlows()
        mViewModel.initState(mSelectedTask)
    }

    override fun onDestroy() {
        super.onDestroy()
        mSharedViewModel.selectedTask = null
    }

    private fun setupUI() {
        mBinding?.apply {
            rvLists.adapter = mTaskListAdapter
            rvLists.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            checkIsActive.setOnCheckedChangeListener(this@SingleTaskBottomSheet)
            buttonDate.setOnClickListener { showDatePickerDialog() }
            buttonTime.setOnClickListener { showTimePickerDialog() }
            buttonTime.setOnLongClickListener { clearAlarmTime() }
            buttonSelectAlarmTime.setOnClickListener { showTimePickerDialog() }
            buttonPriority.setOnClickListener { showPrioritySelectionDialog() }
            fabCreate.setOnClickListener { mViewModel.validate(mBinding?.editTextNote?.text?.toString()) }
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
            ).withSelectionPredicate(SelectionPredicates.createSelectSingleAnything())
                .build()

            mSelectedTask?.let { task ->
                tracker?.select(task.listId)
            }

            mTaskListAdapter.tracker = tracker
        }
    }

    private fun initResources() {
        mCategoriesFailedMessage = resources.getString(R.string.categories_failed_message)
        mPrioritySymbol = resources.getString(R.string.priority_symbol_low)
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
                is Resource.Success -> {
                    mTaskListAdapter.submitList(resource.data)
                    resource.data?.first()?.let {
                        tracker?.select(it.id)
                    }
                }
                is Resource.Error ->
                    Toast.makeText(requireContext(), mCategoriesFailedMessage, Toast.LENGTH_LONG)
                        .show()
                is Resource.Loading -> {}
                is Resource.Empty -> {}
            }
        }
    }

    private suspend fun handleEventsFlow() {
        mViewModel.taskDetailsFlow.collect { details ->
            when (details.event) {
                TaskDetailsEvent.DateChanged ->
                    changeDate(details.date)
                TaskDetailsEvent.TimeChanged ->
                    changeTime(details.time)
                TaskDetailsEvent.Initial ->
                    initState(details)
                TaskDetailsEvent.ListChanged ->
                    changeSelectedListCircleColor(details.list?.color)
                TaskDetailsEvent.PriorityChanged ->
                    changePriorityInEditText(details.priority)
                TaskDetailsEvent.ActivityChanged -> {}
            }
        }
    }

    private suspend fun handleValidationFlow() {
        mViewModel.validationSuccessFlow.collectLatest { validation ->
            if (validation.isValid)
                dismiss()
            else
                Toast.makeText(requireContext(), "Invalid input data.", Toast.LENGTH_LONG)
                    .show()
        }
    }

    private fun initState(details: TaskDetails) = mBinding?.let {
        it.editTextNote.setText(details.titleText ?: "")
        it.checkIsActive.isChecked = details.isActive
        it.circleSelectedList.backgroundTintList =
            ColorStateList.valueOf(details.list?.color ?: Color.TRANSPARENT)

        changeDate(details.date)
        changeTime(details.time)
        changePriorityInEditText(details.priority)
        changeSelectedListCircleColor(details.list?.color)
    }

    private fun changeTime(time: LocalTime?) = mBinding?.buttonTime?.let {
        if (time != null) {
            it.visibility = View.VISIBLE
            it.text = DateTimeUtils.formatTime(time)
        } else {
            it.visibility = View.GONE
            it.text = null
        }
    }

    private fun changeDate(date: LocalDate?) = mBinding?.buttonDate?.let {
        if (date != null) {
            it.visibility = View.VISIBLE
            it.text = DateTimeUtils.formatDate(requireContext(), date)
        } else {
            it.visibility = View.GONE
            it.text = null
        }
    }

    private fun changeSelectedListCircleColor(color: Int?) {
        mBinding?.circleSelectedList?.backgroundTintList =
            ColorStateList.valueOf(color ?: Color.TRANSPARENT)
    }

    private fun showDatePickerDialog() {
        val selectedDate = mViewModel.taskDetailsFlow.value.date ?: LocalDate.now()
        DatePickerDialog(
            requireContext(),
            this,
            selectedDate.year,
            selectedDate.monthValue,
            selectedDate.dayOfMonth
        ).show()
    }

    private fun showTimePickerDialog() {
        val selectedTime = mViewModel.taskDetailsFlow.value.time ?: LocalTime.now()
        TimePickerDialog(
            requireContext(),
            this,
            selectedTime.hour,
            selectedTime.minute,
            true
        ).show()
    }

    private fun showPrioritySelectionDialog() {
        PopupMenu(requireContext(), mBinding?.buttonPriority).also {
            it.inflate(R.menu.menu_task_priority)
            it.setOnMenuItemClickListener(this)
            it.show()
        }
    }

    private fun clearAlarmTime(): Boolean {
        mViewModel.changeTime(null)
        return true
    }

    private var prioritySymbol = ""
    private var clearedEdit = ""

    @SuppressLint("SetTextI18n")
    private fun changePriorityInEditText(priority: PriorityUI?) = mBinding?.editTextNote?.let {
        Log.d("PRIORITY", priority?.symbol ?: "null")
        clearedEdit = it.text.toString().replace(mPrioritySymbol, "", true)
        prioritySymbol = priority?.symbol ?: ""
        it.setText(prioritySymbol + clearedEdit)
        it.setSelection(it.text.length)
    }


    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        mViewModel.changeTime(LocalTime.of(hourOfDay, minute))
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        mViewModel.changeDate(LocalDate.of(year, month, dayOfMonth))
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        mViewModel.changeActivity(isChecked)
    }

    override fun onItemSelected(item: TaskListUI) {
        mViewModel.selectList(item)
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        val priority = PriorityUI.values().find { it.id == item?.itemId }
        mViewModel.changePriority(priority)
        return true
    }
}