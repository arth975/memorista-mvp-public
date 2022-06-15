package com.app.memorista.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.memorista.databinding.FragmentSavedTasksBinding

class SavedTasksFragment : Fragment() {

    private var mBinding: FragmentSavedTasksBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(mBinding == null){
            mBinding = FragmentSavedTasksBinding.inflate(inflater)
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}