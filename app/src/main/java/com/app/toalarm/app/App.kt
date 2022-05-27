package com.app.toalarm.app

import android.app.Application
import com.app.toalarm.di.modules.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * @ClassName: App
 * @Description: class description
 * @Author: Arthur Galoyan
 * @Date: 4/2/2022 9:54 AM
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(listOf(dataModule, domainModule, viewModelsModule))
        }
    }
}