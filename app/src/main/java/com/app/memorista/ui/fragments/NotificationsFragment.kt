package com.app.memorista.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.memorista.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private var mBinding: FragmentNotificationsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mBinding == null) {
            mBinding = FragmentNotificationsBinding.inflate(inflater)
        }
        return mBinding?.root
    }
}