package com.oranle.es.app

import android.app.Application
import com.oranle.es.BuildConfig
import timber.log.Timber

class SessionApp : Application() {

    companion object {
        var instance: SessionApp? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

}