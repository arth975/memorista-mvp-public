package com.app.toalarm.ui.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.fragment.app.Fragment
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

abstract class BaseCalendarDialogFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    protected var mSelectedTaskDateTime: LocalDateTime = LocalDateTime.now()

    protected fun showCalendarPickerDialog() {
        DatePickerDialog(
            requireContext(),
            this,
            mSelectedTaskDateTime.year,
            mSelectedTaskDateTime.monthValue,
            mSelectedTaskDateTime.dayOfMonth
        ).show()
    }
}