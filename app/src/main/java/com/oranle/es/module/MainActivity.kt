package com.oranle.es.module

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.oranle.es.R
import com.oranle.es.databinding.ActivityMainBinding
import com.oranle.es.module.base.BaseActivity
import com.oranle.es.module.base.WebViewActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    fun onInnovationAbility(view: View) {
        Log.d("xxxx", "xczxcv")

        val intent = Intent(view.context, WebViewActivity::class.java)
        val bundle = Bundle()
        bundle.putString("url", "file:///android_asset/indexx.html")
        bundle.putString("title", "用户协议")
        intent.putExtras(bundle)
        view.context.startActivity(intent)

    }
}
