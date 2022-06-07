package com.app.memorista.utils.enums

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class TaskState : Parcelable {
    COMPLETED,
    ACTIVE
}