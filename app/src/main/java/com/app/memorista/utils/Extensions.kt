package com.app.memorista.utils

import android.content.Context
import android.view.WindowInsets
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.app.memorista.R
import com.google.android.material.navigation.NavigationView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun LifecycleOwner.addRepeatedJob(
    state: Lifecycle.State,
    block: suspend CoroutineScope.() -> Unit
): Job = lifecycleScope.launch() {
    repeatOnLifecycle(state, block)
}

fun EditText.showSoftKeyboard() {
    requestFocus()
    val imm = (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
    imm.showSoftInput(this, InputMethodManager.HIDE_IMPLICIT_ONLY)
}

fun NavigationView.changeCornerRadius() {
    val navViewBackground = background as MaterialShapeDrawable
    val radius = resources.getDimension(R.dimen.nav_drawer_border_radius)
    navViewBackground.shapeAppearanceModel = navViewBackground.shapeAppearanceModel
        .toBuilder()
        .setTopRightCorner(CornerFamily.ROUNDED, radius)
        .setBottomRightCorner(CornerFamily.ROUNDED, radius)
        .build()
}