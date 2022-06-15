package com.app.memorista.models

import com.app.memorista.R
import com.app.memorista.app.App

enum class PriorityUI(val symbol: String, val id: Int) {
    LOW(App.appContext.resources.getString(R.string.priority_symbol_low), R.id.priority_low),
    MEDIUM(App.appContext.resources.getString(R.string.priority_symbol_medium), R.id.priority_medium),
    HIGH(App.appContext.resources.getString(R.string.priority_symbol_high), R.id.priority_high),
    EXTRA(App.appContext.resources.getString(R.string.priority_symbol_extra), R.id.priority_extra)
}