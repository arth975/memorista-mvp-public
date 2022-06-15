package com.app.memorista.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

/**
 * @ClassName: Task
 * @Description: class description
 * @Author: Arthur Galoyan
 * @Date: 4/2/2022 9:54 AM
 */
@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    var title: String,
    var description: String,
    var listId: Long,
    val listColor: Int,
    var taskDate: LocalDate? = null,
    var taskTime: LocalTime? = null,
    var alarmTime: LocalTime? = null,
    var isActive: Boolean = false,
    var priorityCode: Int? = null
)