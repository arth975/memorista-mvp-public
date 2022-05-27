package com.app.toalarm.data.local.entities

import androidx.room.ColumnInfo
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
    var note: String,
    var taskDate: LocalDate,
    var taskTime: LocalTime,
    var categoryId: Long,
    var alarmDateTime: LocalDateTime? = null,
    var isActive: Boolean = false
)