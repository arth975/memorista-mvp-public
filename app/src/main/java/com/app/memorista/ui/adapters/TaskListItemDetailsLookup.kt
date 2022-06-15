package com.app.memorista.ui.adapters

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView

class TaskListItemDetailsLookup(private val recyclerView: RecyclerView) :
    ItemDetailsLookup<Long>() {
    override fun getItemDetails(e: MotionEvent): ItemDetails<Long>? {
        val view = recyclerView.findChildViewUnder(e.x, e.y)
        return try {
            (recyclerView.getChildViewHolder(view!!) as TasksListAdapter.TaskListViewHolder).getItemDetails()
        } catch (e: Exception) {
            null
        }
    }
}