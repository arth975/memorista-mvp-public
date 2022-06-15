package com.app.memorista.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.memorista.databinding.ItemTaskBinding
import com.app.memorista.models.TaskUI
import com.app.memorista.utils.GradientContent

/**
 * @ClassName: TaskAdapter
 * @Description: class description
 * @Author: Arthur Galoyan
 * @Date: 3/10/2022 11:27 PM
 */
class TaskAdapter(
    private val onCheckedChangeListener: OnCheckedChangeListener? = null
) : ListAdapter<TaskUI, TaskAdapter.TaskItemViewHolder>(TaskItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemViewHolder {
        return TaskItemViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    @SuppressLint("SetTextI18n")
    inner class TaskItemViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(taskItem: TaskUI) {
            with(binding) {
                task = taskItem
                listColor.background = GradientContent.createGradient(taskItem.listColor)
                checkIsActive.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked != taskItem.isActive)
                        onCheckedChangeListener?.onCheckedChange(taskItem, isChecked)
                }

                if (taskItem.priority != null)
                    textTitle.text = taskItem.priority.symbol + textTitle.text
            }
        }
    }

    private class TaskItemCallback : DiffUtil.ItemCallback<TaskUI>() {
        override fun areItemsTheSame(oldItem: TaskUI, newItem: TaskUI): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TaskUI, newItem: TaskUI): Boolean =
            oldItem == newItem
    }

    interface OnCheckedChangeListener {
        fun onCheckedChange(item: TaskUI, isChecked: Boolean)
    }
}