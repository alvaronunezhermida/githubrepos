package com.dogbreeds.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class DogBreedsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initTimber()
    }

    private fun initTimber() {
        //TODO: Create BuildConfig for debug and release and initialize Timber only in debug
        Timber.plant(Timber.DebugTree())
    }
}