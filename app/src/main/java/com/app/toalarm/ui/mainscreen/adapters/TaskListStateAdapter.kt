package com.app.toalarm.ui.mainscreen.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.app.toalarm.ui.mainscreen.TaskListFragment

/**
 * @ClassName: TaskListStateAdapter
 * @Description: class description
 * @Author: Arthur Galoyan
 * @Date: 3/10/2022 11:36 PM
 */
class TaskListStateAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment = TaskListFragment.newInstance()
}