package com.app.memorista.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.memorista.databinding.ItemTaskCardBinding
import com.app.memorista.models.TaskUI
import com.app.memorista.utils.OnCheckedChange
import com.app.memorista.utils.OnTaskItemClick
import com.app.memorista.utils.OnTaskItemLongClick

/**
 * @ClassName: TaskAdapter
 * @Description: class description
 * @Author: Arthur Galoyan
 * @Date: 3/10/2022 11:27 PM
 */

class TaskAdapter(
    private val onStatusChecked: OnCheckedChange? = null,
    private val onFavoriteStatusChecked: OnCheckedChange? = null,
    private val onItemClick: OnTaskItemClick? = null,
    private val onLongClick: OnTaskItemLongClick? = null
) : ListAdapter<TaskUI, TaskAdapter.TaskItemViewHolder>(TaskItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemViewHolder {
        return TaskItemViewHolder(
            ItemTaskCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    @SuppressLint("SetTextI18n")
    inner class TaskItemViewHolder(private val binding: ItemTaskCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(taskItem: TaskUI) {
            with(binding) {
                task = taskItem
                root.setOnClickListener { onItemClick?.invoke(taskItem) }
                root.setOnLongClickListener {
                    onLongClick?.invoke(root, taskItem)
                    true
                }
                checkIsActive.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked != taskItem.isActive)
                        onStatusChecked?.invoke(taskItem, isChecked)
                }
                imageListColor.setColorFilter(taskItem.listColor)
                /*checkIsFavorite.setOnCheckedChangeListener { _, isChecked ->
                        if (isChecked != taskItem.isFavorite)
                            onFavoriteStatusChecked?.invoke(taskItem, isChecked)
                    }*/

                if (taskItem.priority != null)
                    textNote.text = taskItem.priority.symbol + textNote.text
            }
        }
    }

    private class TaskItemCallback : DiffUtil.ItemCallback<TaskUI>() {
        override fun areItemsTheSame(oldItem: TaskUI, newItem: TaskUI): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TaskUI, newItem: TaskUI): Boolean =
            oldItem == newItem
    }
}