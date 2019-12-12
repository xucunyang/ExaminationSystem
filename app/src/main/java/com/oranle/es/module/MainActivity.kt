package com.oranle.es.module

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.oranle.es.R
import com.oranle.es.databinding.ActivityMainBinding
import com.oranle.es.module.base.BaseActivity
import com.oranle.es.module.base.WebViewActivity
import com.oranle.es.module.base.toast
import com.oranle.es.module.ui.ListActivityDemo

const val SD_WEB_PATH = "sdcard/es-web/web"

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
        bundle.putString("url", "file:///${SD_WEB_PATH}/language_smart.html")
        bundle.putString("title", "用户协议")
        intent.putExtras(bundle)
        view.context.startActivity(intent)

    }


    fun onMultiTest(view: View) {
        toast("${view.id} xxxx")

        val intent = Intent(view.context, ListActivityDemo::class.java)
        view.context.startActivity(intent)

    }
}
