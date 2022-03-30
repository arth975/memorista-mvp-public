package com.app.toalarm.ui.mainscreen.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.toalarm.data.model.Task
import com.app.toalarm.databinding.ItemTaskBinding

/**
 * @ClassName: TaskAdapter
 * @Description: class description
 * @Author: Arthur Galoyan
 * @Date: 3/10/2022 11:27 PM
 */
class TaskAdapter(
    private val tasks: ArrayList<Task>,
    private val onItemClick: (Task?) -> Unit
) : RecyclerView.Adapter<TaskAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount(): Int = tasks.size

    inner class ItemViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root){
            fun bind(task: Task){
                binding.btnEdit.setOnClickListener { onItemClick(task) }
            }
        }
}