package com.app.memorista.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.app.memorista.R
import com.app.memorista.databinding.ActivityMainBinding
import com.app.memorista.ui.bottom_sheets.SingleTaskBottomSheet
import com.app.memorista.utils.changeCornerRadius
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    companion object {
        var fab: FloatingActionButton? = null
    }

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.fabAddNewTask
            .setOnClickListener { SingleTaskBottomSheet().show(supportFragmentManager, "NEW_TASK") }
        mBinding.navDrawer.changeCornerRadius()

        setupNavController()
        fab = mBinding.fabAddNewTask
    }

    private fun setupNavController() {
        val idSet = setOf(
            R.id.introFragment,
            R.id.homeFragment,
            R.id.calendarFragment,
            R.id.favoritesTasksFragment,
            R.id.notificationsFragment,
            R.id.categoriesFragment,
            R.id.upcomingTasksFragment,
            R.id.thisWeekTasksFragment,
            R.id.allTasksFragment
        )
        mNavController =
            (supportFragmentManager.findFragmentById(R.id.nav_host_container) as NavHostFragment).navController

        val appBarConfiguration = AppBarConfiguration(idSet, mBinding.drawerLayout)
        mBinding.navDrawer.setupWithNavController(mNavController)
        mBinding.toolbar.root.setupWithNavController(mNavController, appBarConfiguration)
    }
}