package com.app.memorista.ui.bottom_sheets

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.memorista.R
import com.app.memorista.databinding.FragmentSingleListBinding
import com.app.memorista.ui.adapters.TaskAdapter
import com.app.memorista.ui.viewmodels.SharedViewModel
import com.app.memorista.ui.viewmodels.SingleListViewModel
import com.app.memorista.utils.addRepeatedJob
import com.app.memorista.utils.models.Resource
import com.app.memorista.utils.models.TaskListEvent
import com.skydoves.colorpickerview.ColorEnvelope
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SingleListBottomSheet : ExpandedBottomSheet(), DialogInterface.OnClickListener {

    companion object {
        const val TAG = "SingleListFragment"
    }

    private var mBinding: FragmentSingleListBinding? = null
    private val mViewModel by viewModel<SingleListViewModel>()
    private val mSharedViewModel by sharedViewModel<SharedViewModel>()
    private val mTaskAdapter by lazy { TaskAdapter() }
    private val mBackgroundDrawable by lazy {
        ContextCompat.getDrawable(
            requireContext(),
            R.drawable.background_single_list_sheet
        )!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mBinding == null) {
            mBinding = FragmentSingleListBinding.inflate(layoutInflater)
            initSelectedList()
        }
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupUI()
        handleFlows()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        mViewModel.updateName(mBinding?.textName?.text.toString())
        mViewModel.update()
    }

    private fun setupUI() {
        mBinding?.let {
            it.rvTasks.layoutManager = LinearLayoutManager(requireContext())
            it.rvTasks.adapter = mTaskAdapter
            it.buttonDelete.setOnClickListener { showDeleteDialog() }
            it.buttonColorPicker.setOnClickListener { showColorPickerDialog() }
        }
    }

    private fun showDeleteDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.task_list_delete_dialog_message)
            .setNegativeButton(R.string.task_list_delete_dialog_negative_button_title, null)
            .setPositiveButton(R.string.task_list_delete_dialog_positive_button_title, this)
            .show()
    }

    private fun showColorPickerDialog() {
        ColorPickerDialog.Builder(requireContext())
            .setTitle("Pick the list color")
            .setPreferenceName("")
            .setNegativeButton("Cancel", null)
            .attachBrightnessSlideBar(true)
            .attachAlphaSlideBar(true)
            .setPositiveButton("Apply", object : ColorEnvelopeListener {
                override fun onColorSelected(envelope: ColorEnvelope?, fromUser: Boolean) {
                    mViewModel.updateColor(envelope?.color)
                }
            })
            .show()
    }

    private fun handleFlows() {
        viewLifecycleOwner.addRepeatedJob(Lifecycle.State.STARTED) {
            launch { handleListWithTasksFlow() }
            launch { handleEventsFlow() }
        }
    }

    private suspend fun handleListWithTasksFlow() {
        mViewModel.listWithTasksFlow.collect { resource ->
            when (resource) {
                is Resource.Success ->
                    mTaskAdapter.submitList(resource.data?.tasks)
                is Resource.Error ->
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT)
                        .show()
                is Resource.Loading -> {}
                is Resource.Empty -> {}
            }
        }
    }

    private suspend fun handleEventsFlow() {
        mViewModel.eventFlow.collect { event ->
            when (event) {
                is TaskListEvent.Initial -> {
                    event.data?.let {
                        setBackgroundColor(it.color)
                        mBinding?.list = it
                    }
                }
                is TaskListEvent.ColorChanged ->
                    setBackgroundColor(event.data?.color)
                is TaskListEvent.NameChanged -> {}
            }
        }
    }

    private fun initSelectedList() {
        mSharedViewModel.selectedList.also {
            if (it != null)
                mViewModel.fetchListWithTasks(it.id)
            else
                mViewModel.createNew()
        }
    }

    private fun setBackgroundColor(color: Int?) {
        color?.let {
            val wrappedDrawable = DrawableCompat.wrap(mBackgroundDrawable)
            DrawableCompat.setTint(wrappedDrawable, it)
            mBinding?.rootLayout?.background = wrappedDrawable
        }
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        dismiss()
        mViewModel.delete()
    }
}