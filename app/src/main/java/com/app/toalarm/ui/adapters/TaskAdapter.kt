package com.app.toalarm.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.toalarm.databinding.ItemTaskBinding
import com.app.toalarm.models.TaskUI

/**
 * @ClassName: TaskAdapter
 * @Description: class description
 * @Author: Arthur Galoyan
 * @Date: 3/10/2022 11:27 PM
 */
class TaskAdapter(
    private val onItemClick: (TaskUI?) -> Unit,
) : ListAdapter<TaskUI, TaskAdapter.TaskItemViewHolder>(TaskItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemViewHolder {
        return TaskItemViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TaskItemViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(taskItem: TaskUI) {
            with(binding) {
                btnEdit.setOnClickListener { onItemClick(taskItem) }
                task = taskItem
            }
        }
    }
}

private class TaskItemCallback : DiffUtil.ItemCallback<TaskUI>() {
    override fun areItemsTheSame(oldItem: TaskUI, newItem: TaskUI): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TaskUI, newItem: TaskUI): Boolean {
        return oldItem == newItem
    }

}