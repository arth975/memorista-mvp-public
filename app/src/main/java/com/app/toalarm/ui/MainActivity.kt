package com.app.toalarm.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.app.toalarm.R
import com.app.toalarm.databinding.ActivityMainBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

class MainActivity : AppCompatActivity() {

    private var mBinding: ActivityMainBinding? = null
    private lateinit var mNavController: NavController

    private val mCurrentDate = LocalDate.now()
    private val mLocale = Locale.ENGLISH
    private val mWeekDayFormatter = DateTimeFormatter.ofPattern("EEEE", mLocale)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding?.root)
        setupToolbar()
    }

    private fun setupToolbar() {
        setupNavController()
        setupCurrentDate()
        val appBarConfiguration = AppBarConfiguration(mNavController.graph)
        mBinding?.toolbar?.root?.setupWithNavController(mNavController, appBarConfiguration)
    }

    private fun setupNavController() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        mNavController = navHostFragment.navController
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