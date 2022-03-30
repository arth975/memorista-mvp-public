package com.app.toalarm.ui.newtaskscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.toalarm.databinding.FragmentAddNewTaskBinding
import com.app.toalarm.ui.mainscreen.adapters.TasksListSpinnerAdapter

/**
 * @ClassName: AddNewTaskFragment
 * @Description: class description
 * @Author: Arthur Galoyan
 * @Date: 3/9/2022 11:14 PM
 */
class AddNewTaskFragment: Fragment() {

    private val taskLists = arrayListOf(
        "Default List",
        "Work",
        "Fitnes",
        "Custom list"
    )

    private var mBinding: FragmentAddNewTaskBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(mBinding == null){
            mBinding = FragmentAddNewTaskBinding.inflate(inflater).apply {
                spinnerCategories.adapter = TasksListSpinnerAdapter(requireContext(), taskLists)
            }
        }
        return mBinding?.root
    }
}