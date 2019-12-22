package com.oranle.es.module.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.oranle.es.app.SessionApp
import com.oranle.es.module.ui.home.HomeActivity

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("xcy", "on receiver action ${intent?.action}")
        val i = Intent(SessionApp.instance, HomeActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context?.startActivity(i)
    }

}