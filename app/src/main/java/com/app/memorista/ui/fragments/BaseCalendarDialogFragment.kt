package com.app.memorista.ui.fragments

import android.app.DatePickerDialog
import androidx.fragment.app.Fragment
import java.time.LocalDateTime

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