package com.oranle.es.app

import android.app.Application

class SessionApp : Application() {

    companion object {
        var instance: SessionApp? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}