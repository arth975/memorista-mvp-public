package com.app.toalarm.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object Utils {

    const val EMPTY = "--"
    val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM")

    fun toStringDate(date: LocalDate, formatter: DateTimeFormatter = dateFormatter): String{
        return date.format(formatter)
    }
}