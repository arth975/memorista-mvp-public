package com.app.toalarm.utils.bind.adapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.app.toalarm.utils.Utils
import java.time.LocalDate

@BindingAdapter("dateText")
fun localDate(view: TextView, date: LocalDate?){
    view.text = if(date == null) Utils.EMPTY else Utils.toStringDate(date, Utils.dateFormatter)
}