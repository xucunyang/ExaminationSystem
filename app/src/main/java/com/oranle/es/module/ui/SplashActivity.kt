package com.oranle.es.module.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.oranle.es.R
import com.oranle.es.databinding.ActivitySplashBinding
import com.oranle.es.module.base.BaseActivity
import com.oranle.es.module.ui.home.HomeActivity

class SplashActivity: BaseActivity<ActivitySplashBinding>() {

    override val layoutId: Int
        get() = R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler().postDelayed(Runnable {
            startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
        }, 200)
    }

}