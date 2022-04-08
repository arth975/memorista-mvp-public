package com.app.toalarm.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

/**
 * @ClassName: Task
 * @Description: class description
 * @Author: Arthur Galoyan
 * @Date: 4/2/2022 9:54 AM
 */
@Entity(tableName = "tasks")
data class Task(
    var title: String,
    var note: String,
    @ColumnInfo(name = "alarm_date_time") var alarmDateTime: LocalDateTime?,
    @ColumnInfo(name = "is_active") var isActive: Boolean = false,
    @ColumnInfo(name = "created_at") var createdAt: LocalDateTime? = null,
    @ColumnInfo(name = "updated_at") var updatedAt: LocalDateTime? = null,
    @PrimaryKey(autoGenerate = true) val id: Long? = null
)