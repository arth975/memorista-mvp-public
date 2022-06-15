package com.app.memorista.utils

import android.view.View
import android.view.animation.AnimationUtils
import com.app.memorista.R

object AnimationContent {

    private const val FALLING_DOWN = R.anim.falling_down
    private const val FALLING_UP = R.anim.falling_up

    fun fallingDown(view: View?) {
        view?.startAnimation(AnimationUtils.loadAnimation(view.context, FALLING_DOWN))
    }

    fun fallingUp(view: View?) {
        view?.startAnimation(AnimationUtils.loadAnimation(view.context, FALLING_UP))
    }
}