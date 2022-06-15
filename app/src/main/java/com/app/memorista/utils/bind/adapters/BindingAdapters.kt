package com.app.memorista.utils.bind.adapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.app.memorista.utils.DateTimeUtils
import com.app.memorista.utils.Utils
import java.time.LocalDate
import java.time.LocalTime

@BindingAdapter("dateText")
fun localDate(view: TextView, date: LocalDate?){
    view.text = DateTimeUtils.formatDate(view.context, date)
}

@BindingAdapter("timeText")
fun localTime(view: TextView, time: LocalTime?) {
    view.text = DateTimeUtils.formatTime(time)
}