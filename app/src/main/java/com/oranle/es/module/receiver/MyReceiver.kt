package com.oranle.es.module.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.oranle.es.app.SessionApp
import com.oranle.es.module.ui.SplashActivity
import timber.log.Timber

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Timber.d("on receiver action ${intent?.action}")
        val i = Intent(SessionApp.instance, SplashActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context?.startActivity(i)
    }

}