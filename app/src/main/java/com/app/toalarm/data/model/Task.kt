package com.app.toalarm.data.model

import java.time.LocalDateTime

/**
 * @ClassName: Task
 * @Description: class description
 * @Author: Arthur Galoyan
 * @Date: 3/10/2022 11:25 PM
 */
data class Task (
    val title: String,
    val description: String,
    val dateTime: LocalDateTime,
    val isCompleted: Boolean = false
)