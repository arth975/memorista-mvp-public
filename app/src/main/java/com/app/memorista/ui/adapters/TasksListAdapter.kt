package com.app.memorista.ui.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.memorista.databinding.ItemTaskListBinding
import com.app.memorista.models.TaskListUI

class TasksListAdapter(
    private val onItemClickListener: OnItemClickListener? = null
) : ListAdapter<TaskListUI, TasksListAdapter.ItemViewHolder>(ItemCallback()) {

    init {
        setHasStableIds(true)
    }

    var tracker: SelectionTracker<Long>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemTaskListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            ),
            onItemClickListener
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        if (tracker != null)
            holder.bind(getItem(position), tracker!!.isSelected(position.toLong()))
        else
            holder.bind(getItem(position))
    }

    override fun getItemId(position: Int): Long = position.toLong()

    inner class ItemViewHolder(
        private val binding: ItemTaskListBinding,
        private val onItemClickListener: OnItemClickListener? = null
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TaskListUI) {
            binding.list = item
            binding.root.setOnClickListener { onItemClickListener?.onItemClick(item) }
        }

        fun bind(item: TaskListUI, isSelected: Boolean) {
            binding.list = item
            binding.taskListCard.strokeColor = if (isSelected) Color.RED else Color.TRANSPARENT
            Log.d("SELECTION_TEST", "$bindingAdapterPosition is selected $isSelected")
        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition(): Int = bindingAdapterPosition
                override fun getSelectionKey(): Long = getItem(bindingAdapterPosition).id
                override fun inSelectionHotspot(e: MotionEvent): Boolean = true
            }
    }

    class ItemCallback : DiffUtil.ItemCallback<TaskListUI>() {
        override fun areItemsTheSame(oldItem: TaskListUI, newItem: TaskListUI): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TaskListUI, newItem: TaskListUI): Boolean =
            oldItem == newItem
    }

    interface OnItemClickListener {
        fun onItemClick(item: TaskListUI)
    }
}