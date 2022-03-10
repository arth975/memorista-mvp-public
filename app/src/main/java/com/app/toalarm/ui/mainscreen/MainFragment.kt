package com.app.toalarm.ui.mainscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.toalarm.R
import com.app.toalarm.data.model.Task
import com.app.toalarm.databinding.FragmentMainBinding
import com.app.toalarm.ui.mainscreen.adapters.TaskListStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import java.time.LocalDateTime

/**
 * @ClassName: MainFragment
 * @Description: class description
 * @Author: Arthur Galoyan
 * @Date: 3/9/2022 11:14 PM
 */
class MainFragment : Fragment() {

    private lateinit var mBinding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentMainBinding.inflate(inflater)
        setupViewPager()

        return mBinding.root
    }

    private fun setupViewPager(){
        val titles = resources.getStringArray(R.array.task_status_tab_titles).toCollection(ArrayList())
        with(mBinding){
            vpTasks.adapter = TaskListStateAdapter(this@MainFragment)
            TabLayoutMediator(tlTaskStatus, vpTasks) { tab, pos ->
                tab.text = titles[pos]
            }.attach()
        }
    }
}