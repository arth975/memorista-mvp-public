package com.app.memorista.utils

import android.content.Context
import android.util.Log
import com.app.memorista.R
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.WeekFields
import java.util.*

object DateTimeUtils {

    private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM")
    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    private const val EMPTY = "--"

    fun formatDate(context: Context, date: LocalDate?): String = when {
        date == null -> EMPTY
        date.isEqual(LocalDate.now()) ->
            context.resources.getString(R.string.date_today)
        date.isEqual(LocalDate.now().minusDays(1)) ->
            context.resources.getString(R.string.date_yesterday)
        date.isEqual(LocalDate.now().plusDays(1)) ->
            context.resources.getString(R.string.date_tomorrow)
        /*isCurrentWeek(date) -> {
            Log.d("DAY_OF_WEEK", date.format(dateFormatter))
            Log.d("DAY_OF_WEEK", date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault()))
            date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
        }*/
        else ->
            date.format(dateFormatter)
    }

    fun formatTime(time: LocalTime?): String = time?.format(timeFormatter) ?: EMPTY

    private fun isCurrentWeek(date: LocalDate): Boolean {
        val currentDay = LocalDate.now()

        val startDay = currentDay.with(WeekFields.of(Locale.getDefault()).firstDayOfWeek)//with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 1L)
        val endDay = startDay.plusDays(7)//currentDay.with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 7L)
        Log.d("DAY_OF_WEEK", "Start day: ${startDay.format(DateTimeFormatter.ISO_DATE)}")
        Log.d("DAY_OF_WEEK", "End day: ${endDay.format(DateTimeFormatter.ISO_DATE)}")
        Log.d("DAY_OF_WEEK", "Current day: ${currentDay.dayOfWeek}")
        Log.d("DAY_OF_WEEK", "First day of week: ${WeekFields.of(Locale.getDefault()).firstDayOfWeek}")
        return (date.isBefore(endDay) || date.isEqual(endDay)) &&
                (date.isAfter(startDay) || date.isEqual(startDay))
    }
}