package com.app.memorista.data.utils

import androidx.room.TypeConverter
import java.time.*
import java.time.format.DateTimeFormatter

/**
 * @ClassName: DateConverter
 * @Description: class description
 * @Author: Arthur Galoyan
 * @Date: 4/2/2022 10:13 AM
 */
class DateTimeConverter {

    private val mZone = ZoneId.systemDefault()

    @TypeConverter
    fun timeStampToDateTime(timestamp: Long?): LocalDateTime? {
        return timestamp?.let { Instant.ofEpochMilli(it).atZone(mZone).toLocalDateTime() }
    }

    @TypeConverter
    fun dateTimeToTimeStamp(date: LocalDateTime?): Long? {
        return date?.atZone(mZone)?.toInstant()?.toEpochMilli()
    }

    @TypeConverter
    fun timeStampToDate(timestamp: Long?): LocalDate? {
        return timestamp?.let { Instant.ofEpochMilli(it).atZone(mZone).toLocalDate() }
    }

    @TypeConverter
    fun dateToTimeStamp(date: LocalDate?): Long? {
        return date?.atStartOfDay(mZone)?.toInstant()?.toEpochMilli()
    }

    @TypeConverter
    fun timeStampToTime(timeString: String?): LocalTime? {
        return LocalTime.parse(timeString, DateTimeFormatter.ISO_TIME)
    }

    @TypeConverter
    fun timeToTimeStamp(time: LocalTime?): String? {
        return time?.format(DateTimeFormatter.ISO_TIME)
    }
}