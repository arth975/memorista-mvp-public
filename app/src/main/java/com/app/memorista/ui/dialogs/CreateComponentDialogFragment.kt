package com.app.memorista.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.app.memorista.R
import com.app.memorista.databinding.DialogCreateComponentBinding
import com.app.memorista.ui.viewmodels.SharedViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CreateComponentDialogFragment : DialogFragment() {

    private var mBinding: DialogCreateComponentBinding? = null
    private val mSharedViewModel by sharedViewModel<SharedViewModel>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (mBinding == null)
            mBinding = DialogCreateComponentBinding.inflate(layoutInflater).apply {
                buttonCreateTask.setOnClickListener {
                    findNavController().navigate(R.id.action_createComponentDialogFragment_to_createTaskFragment2)
                    dismiss()
                }
                buttonCreateList.setOnClickListener { createNewList() }
            }

        return createDialog()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setLayout(
            resources.getDimension(R.dimen.create_component_dialog_width).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    private fun createDialog(): Dialog {
        return AlertDialog.Builder(requireContext())
            .setView(mBinding?.root)
            .create()
            .also { it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) }
    }

    private fun createNewList() {
        mSharedViewModel.selectedList = null
        SingleListBottomSheetDialog().show(parentFragmentManager, SingleListBottomSheetDialog.TAG)
        dismiss()
    }
}