package com.app.memorista.ui.adapters

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.memorista.databinding.ItemTaskListBinding
import com.app.memorista.databinding.ItemTaskListNewBinding
import com.app.memorista.models.TaskListUI
import com.app.memorista.utils.GradientContent

class TasksListAdapter(
    private val onItemClickListener: OnItemClickListener? = null,
    private val onItemSelectedListener: OnItemSelectedListener? = null,
    private val onNewItemClick: OnCreateNewItemClickListener? = null
) : ListAdapter<TaskListUI, TasksListAdapter.ItemViewHolder>(ItemCallback()) {

    init {
        setHasStableIds(true)
    }

    companion object {
        private const val CREATE_LIST_ITEM = 0
        private const val SIMPLE_ITEM = 1
    }

    var tracker: SelectionTracker<Long>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        when (viewType) {
            CREATE_LIST_ITEM -> CreateListViewHolder(
                ItemTaskListNewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
            )
            else -> {
                TaskListViewHolder(
                    ItemTaskListBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                    )
                )
            }
        }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        when {
            position == itemCount - 1 ->
                holder.bind()
            tracker != null ->
                holder.bind(getItem(position), tracker!!.isSelected(getItem(position).id))
            else ->
                holder.bind(getItem(position))
        }
    }

    override fun getItemId(position: Int): Long =
        if (position != currentList.size) currentList[position].hashCode()
            .toLong() else position.toLong()

    override fun getItemCount(): Int = currentList.size + 1
    override fun getItemViewType(position: Int): Int =
        if (position == itemCount - 1) CREATE_LIST_ITEM else SIMPLE_ITEM

    abstract class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        open fun bind() {}
        open fun bind(item: TaskListUI) {}
        open fun bind(item: TaskListUI, isSelected: Boolean) {}
    }

    inner class TaskListViewHolder(private val binding: ItemTaskListBinding) :
        ItemViewHolder(binding.root) {
        override fun bind(item: TaskListUI) {
            binding.list = item
            binding.rootLayout.background = GradientContent.createGradient(item.color)
            binding.indicatorCompletedTasksPercent
                .setProgressCompat(item.completedTasksPercent, true)
            binding.root.setOnClickListener { onItemClickListener?.onItemClick(item) }
        }

        override fun bind(item: TaskListUI, isSelected: Boolean) {
            bind(item)
            binding.imageCheckCircle.visibility = if (isSelected) View.VISIBLE else View.GONE
            if (isSelected)
                onItemSelectedListener?.onItemSelected(item)
        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition(): Int = absoluteAdapterPosition
                override fun getSelectionKey(): Long = getItem(absoluteAdapterPosition).id
                override fun inSelectionHotspot(e: MotionEvent): Boolean = true
            }
    }

    inner class CreateListViewHolder(private val binding: ItemTaskListNewBinding) :
        ItemViewHolder(binding.root) {
        override fun bind() {
            binding.root.setOnClickListener { onNewItemClick?.onCreateNewItemClick() }
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

    interface OnItemSelectedListener {
        fun onItemSelected(item: TaskListUI)
    }

    interface OnCreateNewItemClickListener {
        fun onCreateNewItemClick()
    }
}