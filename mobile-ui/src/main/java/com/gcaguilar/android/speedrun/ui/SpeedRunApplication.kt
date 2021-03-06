package com.gcaguilar.android.speedrun.ui

import android.app.Application
import com.gcaguilar.android.speedrun.ui.di.applicationModule
import com.gcaguilar.android.speedrun.ui.di.gameModule
import net.danlew.android.joda.JodaTimeAndroid
import org.koin.android.ext.android.startKoin
import timber.log.Timber

class SpeedRunApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        JodaTimeAndroid.init(this)
        startKoin(this, listOf(applicationModule, gameModule))
        setupTimber()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
