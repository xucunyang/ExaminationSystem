package com.oranle.es.module

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.oranle.es.R
import com.oranle.es.databinding.ActivityMainBinding
import com.oranle.es.module.base.BaseActivity
import com.oranle.es.module.base.WebViewActivity
import com.oranle.es.module.base.toast
import com.oranle.es.module.examination.inportFile.FileImportActivity
import timber.log.Timber

const val SD_WEB_PATH = "sdcard/es-web/web"

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    fun onInnovationAbility(view: View) {
        Timber.d("xczxcv")

        val intent = Intent(view.context, WebViewActivity::class.java)
        val bundle = Bundle()
        bundle.putString("url", "file:///${SD_WEB_PATH}/laguage/language_smart.html")
        bundle.putString("title", "用户协议")
        intent.putExtras(bundle)
        view.context.startActivity(intent)

    }


    fun onMultiTest(view: View) {
        toast("${view.id} xxxx")

//        val intent = Intent(view.context, ListActivityDemo::class.java)
        val intent = Intent(view.context, FileImportActivity::class.java)
//        val intent = Intent(view.context, ExaminationSystemLoginActivity::class.java)
        view.context.startActivity(intent)



    }
}
