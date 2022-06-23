package com.app.memorista.ui.bottom_sheets

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class ExpandedBottomSheet() : BottomSheetDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = (super.onCreateDialog(savedInstanceState) as BottomSheetDialog)
        dialog.behavior.also {
            it.state = BottomSheetBehavior.STATE_EXPANDED
            it.skipCollapsed = true
            it.peekHeight = Resources.getSystem().displayMetrics.heightPixels
        }
        return dialog
    }

    override fun onStart() {
        super.onStart()
        val metrics = DisplayMetrics()
        requireActivity().windowManager?.defaultDisplay?.getMetrics(metrics)
        view?.layoutParams?.height = metrics.heightPixels
        view?.requestLayout()
    }
}