package com.app.memorista.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.app.memorista.R
import com.app.memorista.databinding.ActivityMainBinding
import com.app.memorista.ui.dialogs.CreateTaskBottomSheetDialog
import com.app.memorista.utils.changeCornerRadius
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    companion object {
        var fab: FloatingActionButton? = null
        var binding: ActivityMainBinding? = null
    }

    private lateinit var mNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.fabAddNewTask
            ?.setOnClickListener { CreateTaskBottomSheetDialog().show(supportFragmentManager, "NEW_TASK") }
        binding?.navDrawer?.changeCornerRadius()

        setupNavController()
        fab = binding?.fabAddNewTask
    }

    private fun setupNavController() {
        val idSet = setOf(
            R.id.introFragment,
            R.id.homeFragment,
            R.id.calendarFragment,
            R.id.savedTasksFragment,
            R.id.notificationsFragment,
            R.id.categoriesFragment,
            R.id.tomorrowTasksFragment,
            R.id.thisWeekTasksFragment,
            R.id.allTasksFragment
        )
        mNavController =
            (supportFragmentManager.findFragmentById(R.id.nav_host_container) as NavHostFragment).navController

        val appBarConfiguration = AppBarConfiguration(idSet, binding?.drawerLayout)
        binding?.navDrawer?.setupWithNavController(mNavController)
        binding?.toolbar?.root?.setupWithNavController(mNavController, appBarConfiguration)
    }
}