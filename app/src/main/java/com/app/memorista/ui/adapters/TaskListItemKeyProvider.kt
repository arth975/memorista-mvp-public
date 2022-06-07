package com.app.memorista.ui.adapters

import androidx.recyclerview.selection.ItemKeyProvider

class TaskListItemKeyProvider(private val adapter: TasksListAdapter) :
    ItemKeyProvider<Long>(SCOPE_CACHED) {

    override fun getKey(position: Int): Long = adapter.currentList[position].id

    override fun getPosition(key: Long): Int = adapter.currentList.indexOfFirst { it.id == key }
}