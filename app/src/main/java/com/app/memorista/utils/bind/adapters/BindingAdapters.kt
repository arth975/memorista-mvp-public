package com.app.memorista.utils.bind.adapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.app.memorista.utils.Utils
import java.time.LocalDate
import java.time.LocalTime

@BindingAdapter("dateText")
fun localDate(view: TextView, date: LocalDate?){
    view.text = if(date == null) Utils.EMPTY else Utils.toStringDate(date, Utils.dateFormatter)
}

@BindingAdapter("timeText")
fun localTime(view: TextView, time: LocalTime?) {
    view.text = if(time == null) Utils.EMPTY else Utils.toStringTime(time)
}