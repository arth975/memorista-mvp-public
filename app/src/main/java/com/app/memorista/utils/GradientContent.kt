package com.app.memorista.utils

import android.graphics.drawable.GradientDrawable
import androidx.core.graphics.ColorUtils

object GradientContent {

    fun createGradient(color: Int, endColorAlpha: Int = 80): GradientDrawable {
        val colors = intArrayOf(
            color,
            ColorUtils.setAlphaComponent(color, endColorAlpha),
        )
        return GradientDrawable(GradientDrawable.Orientation.BL_TR, colors)
    }
}