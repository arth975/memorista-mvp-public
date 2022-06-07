package com.app.memorista.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.app.memorista.R
import com.app.memorista.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val NO_LIMITS_FLAG = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        private val LOCALE = Locale.ENGLISH
        var fab: FloatingActionButton? = null
    }

    var mBinding: ActivityMainBinding? = null
    private lateinit var mNavController: NavController

    private val mCurrentDate = LocalDate.now()
    private val mWeekDayFormatter = DateTimeFormatter.ofPattern("EEEE", LOCALE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding?.root)

        setupNavController()
        setupToolbar()
        fab = mBinding?.fabAddNewTask
    }

    @SuppressLint("SetTextI18n")
    private fun setupToolbar() {
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.introFragment, R.id.homeFragment))
        mBinding?.toolbar?.apply {
            root.setupWithNavController(mNavController, appBarConfiguration)
            root.visibility = View.GONE
            textWeekday.text = "${mCurrentDate.format(mWeekDayFormatter)}, "
            textDate.text = formatDate()
        }
        window.addFlags(NO_LIMITS_FLAG)
    }

    private fun setupNavController() {
        mNavController =
            (supportFragmentManager.findFragmentById(R.id.nav_host_container) as NavHostFragment).navController
    }

    private fun formatDate(): String {
        val suffix = when (mCurrentDate.dayOfMonth) {
            1 -> "st"
            2 -> "nd"
            3 -> "rd"
            else -> "th"
        }
        val month = mCurrentDate.month.getDisplayName(TextStyle.FULL, LOCALE)
        return "${mCurrentDate.dayOfMonth}$suffix $month"
    }
}