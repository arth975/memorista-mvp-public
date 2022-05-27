package com.app.toalarm.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.app.toalarm.databinding.ItemSpinnerTasksListBinding
import com.app.toalarm.databinding.ItemSpinnerTasksListDropdownBinding
import com.app.toalarm.models.CategoryUI

/**
 * @ClassName: TasksListSpinnerAdapter
 * @Description: class description
 * @Author: Arthur Galoyan
 * @Date: 3/29/2022 11:39 AM
 */
class CategoriesSpinnerAdapter(private val context: Context, val items: List<CategoryUI>) :
    BaseAdapter() {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int = items.size
    override fun getItem(p0: Int): Any = ""
    override fun getItemId(p0: Int): Long = 0

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        return ItemSpinnerTasksListBinding
            .inflate(layoutInflater, parent, false)
            .apply { tvListName.text = items[position].name }
            .root
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return ItemSpinnerTasksListDropdownBinding
            .inflate(layoutInflater, parent, false)
            .apply {
                tvListName.text = items[position].name
                if(position == 0) imgArrowDown.visibility = View.VISIBLE
                if(position == items.size - 1) underline.visibility = View.INVISIBLE
            }
            .root
    }
}