package com.oranle.es.module.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.oranle.es.R
import com.oranle.es.databinding.ActivitySplashBinding
import com.oranle.es.module.MainActivity
import com.oranle.es.module.base.BaseActivity

class SplashActivity: BaseActivity<ActivitySplashBinding>() {

    override val layoutId: Int
        get() = R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler().postDelayed(Runnable {
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        }, 200)
    }

}