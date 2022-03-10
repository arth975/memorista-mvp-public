package com.app.toalarm.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.app.toalarm.R
import com.app.toalarm.databinding.ActivityMainBinding
import com.app.toalarm.utils.ViewAnimator
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

class MainActivity : AppCompatActivity() {

    private var mBinding: ActivityMainBinding? = null

    private val mCurrentDate = LocalDate.now()
    private val mLocale = Locale.ENGLISH
    private val mWeekDayFormatter = DateTimeFormatter.ofPattern("dddd", mLocale)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater).apply {
            toolbar.btnBack.setOnClickListener { findNavController(R.id.nav_host_fragment).navigateUp() }
        }
        setupCurrentDate()

        setContentView(mBinding?.root)
    }

    fun showBackButton() {
        mBinding?.let {
            ViewAnimator.crossFade(it.toolbar.btnBack, it.toolbar.spinnerCategories)
        }
    }

    fun showCategoriesSpinner() {
        mBinding?.let {
            ViewAnimator.crossFade(it.toolbar.spinnerCategories, it.toolbar.btnBack)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupCurrentDate() {
        try {
            mBinding?.let {
                with(it.toolbar) {
                    tvWeekday.text = "${mCurrentDate.format(mWeekDayFormatter)}, "
                    tvDate.text = getDate()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getDate(): String {
        val suffix = when (mCurrentDate.dayOfMonth) {
            1 -> "st"
            2 -> "nd"
            3 -> "rd"
            else -> "th"
        }
        val month = mCurrentDate.month.getDisplayName(TextStyle.FULL, mLocale)
        return "${mCurrentDate.dayOfMonth}$suffix $month"
    }
}