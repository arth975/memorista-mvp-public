package com.app.toalarm.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.app.toalarm.databinding.DialogCreateCategoryBinding
import com.app.toalarm.ui.viewmodels.CreateCategoryDialogViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateCategoryDialogFragment : DialogFragment() {

    private var mBinding: DialogCreateCategoryBinding? = null
    private val mCreateCategoryViewModel by viewModel<CreateCategoryDialogViewModel>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (mBinding == null)
            mBinding = DialogCreateCategoryBinding.inflate(layoutInflater).apply {
                btnCreateCategory.setOnClickListener { onCreateButtonClick() }
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

    private fun createDialog(): Dialog {
        val dialog = AlertDialog.Builder(requireContext())
            .setView(mBinding?.root)
            .create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    private fun onCreateButtonClick() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            val categoryName = mBinding?.etCategoryName?.text.toString()
            if(mCreateCategoryViewModel.createCategory(categoryName))
                dismiss()
            else
                Toast.makeText(requireContext(), "Input category name", Toast.LENGTH_LONG).show()
        }
    }
}