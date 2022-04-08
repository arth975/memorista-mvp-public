package com.app.toalarm.utils.db.type.converters

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

/**
 * @ClassName: DateConverter
 * @Description: class description
 * @Author: Arthur Galoyan
 * @Date: 4/2/2022 10:13 AM
 */
class DateTimeConverter {

    private val mZone = ZoneId.systemDefault()

    @TypeConverter
    fun fromTimeStamp(timestamp: Long?): LocalDateTime?{
        return timestamp?.let { Instant.ofEpochMilli(it).atZone(mZone).toLocalDateTime() }
    }

    @TypeConverter
    fun dateToTimeStamp(date: LocalDateTime?): Long?{
        return date?.atZone(mZone)?.toInstant()?.toEpochMilli()
    }
}