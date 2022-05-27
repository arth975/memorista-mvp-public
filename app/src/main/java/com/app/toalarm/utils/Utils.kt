package com.app.toalarm.utils

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object Utils {

    const val EMPTY = "--"
    val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM")
    val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    fun toStringDate(date: LocalDate, formatter: DateTimeFormatter = dateFormatter): String =
        date.format(formatter)

    fun toStringTime(time: LocalTime, formatter: DateTimeFormatter = timeFormatter): String =
        time.format(formatter)
}