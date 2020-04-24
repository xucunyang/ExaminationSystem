package com.oranle.es.app

import android.app.Application
import com.oranle.es.BuildConfig
import com.oranle.es.data.DB
import com.oranle.es.data.repository.DBRepository
import com.oranle.es.module.init.AppInit
import com.oranle.es.module.init.AppInitImpl
import com.tencent.bugly.crashreport.CrashReport
import timber.log.Timber

class SessionApp : Application() {

    val localDB: DB
        get() = DBRepository.getDB()

    companion object {
        var instance: SessionApp? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        // bugly
        CrashReport.initCrashReport(applicationContext, "e87fb5e233", BuildConfig.DEBUG)

        val appInit: AppInit = AppInitImpl()
        appInit.rootUserInit()

    }

}