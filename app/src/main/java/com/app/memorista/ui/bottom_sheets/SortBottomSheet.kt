package com.app.memorista.ui.bottom_sheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.memorista.databinding.DialogSortBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SortBottomSheet : BottomSheetDialogFragment() {

    private var mBinding: DialogSortBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(mBinding == null)
            mBinding = DialogSortBinding.inflate(inflater)
        return mBinding?.root
    }
}