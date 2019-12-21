package com.oranle.es.app

import android.app.Application
import com.oranle.es.BuildConfig
import com.oranle.es.data.DB
import com.oranle.es.data.repository.DBRepository
import com.oranle.es.module.init.AppInit
import com.oranle.es.module.init.AppInitImpl
import timber.log.Timber

class SessionApp : Application() {

    val localDB: DB
        get() = DBRepository.getDB(this)

    companion object {
        var instance: SessionApp? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        val appInit: AppInit = AppInitImpl()
        appInit.rootUserInit()

    }

}