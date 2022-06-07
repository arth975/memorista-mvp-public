package com.app.memorista.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lists")
data class ListEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val tasksCount: Int,
    val completedTasksPercent: Int,
    val color: Int
)