package com.app.memorista.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View

/**
 * @ClassName: ViewAnimator
 * @Description: class description
 * @Author: Arthur Galoyan
 * @Date: 3/10/2022 11:14 PM
 */
object ViewAnimator {

    const val DURATION = 200L

    private const val HIDE_ALPHA = 0f
    private const val SHOW_ALPHA = 1f

    fun crossFade(show: View?, hide: View?, duration: Long = DURATION) {
        fadeIn(show, duration)
        fadeOut(hide, duration)
    }

    fun fadeIn(view: View?, duration: Long = DURATION) {
        view?.apply {
            alpha = HIDE_ALPHA
            visibility = View.VISIBLE
            animate()
                .alpha(SHOW_ALPHA)
                .setDuration(duration)
                .setListener(null)
        }
    }

    fun fadeOut(view: View?, duration: Long = DURATION) {
        view?.apply {
            alpha = SHOW_ALPHA
            visibility = View.VISIBLE
            animate()
                .alpha(HIDE_ALPHA)
                .setDuration(duration)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        visibility = View.GONE
                    }
                })
        }
    }
}