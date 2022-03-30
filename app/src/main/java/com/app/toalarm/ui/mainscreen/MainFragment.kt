package com.app.toalarm.ui.mainscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.app.toalarm.R
import com.app.toalarm.data.model.Task
import com.app.toalarm.databinding.FragmentMainBinding
import com.app.toalarm.ui.MainActivity
import com.app.toalarm.ui.mainscreen.adapters.TaskListStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * @ClassName: MainFragment
 * @Description: class description
 * @Author: Arthur Galoyan
 * @Date: 3/9/2022 11:14 PM
 */
class MainFragment : Fragment() {

    private var mBinding: FragmentMainBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mBinding == null) {
            mBinding = FragmentMainBinding.inflate(inflater).apply {
                selectedDate = LocalDate.now()
                fabAddNewTask.setOnClickListener { onFabClick() }
            }
            setupViewPager()
        }
        return mBinding?.root
    }

    private fun setupViewPager() {
        mBinding?.apply {
            val titles = resources.getStringArray(R.array.task_status_tab_titles).toCollection(ArrayList())
            vpTasks.adapter = TaskListStateAdapter(this@MainFragment)
            vpTasks.isSaveEnabled = false

            TabLayoutMediator(tlTaskStatus, vpTasks) { tab, pos ->
                tab.text = titles[pos]
            }.attach()
        }
    }

    private fun onFabClick() {
        try {
            (requireActivity() as MainActivity).showBackButton()
            findNavController().navigate(R.id.action_mainFragment_to_addNewTaskFragment)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}