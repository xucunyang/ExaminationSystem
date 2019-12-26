package com.oranle.es.module.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.oranle.es.R
import com.oranle.es.databinding.ActivityMainBinding
import com.oranle.es.module.base.BaseActivity
import com.oranle.es.module.base.WebViewActivity
import com.oranle.es.module.base.toast
import com.oranle.es.module.ui.login.ExaminationSystemLoginActivity

const val SD_WEB_PATH = "sdcard/es-web/web"

class HomeActivity : BaseActivity<ActivityMainBinding>() {

    override val layoutId: Int
        get() = R.layout.activity_main

    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = getViewModel()
        initDialog(viewModel)
    }

    fun onInnovationAbility(view: View) {

        val intent = Intent(view.context, WebViewActivity::class.java)
        val bundle = Bundle()
//        bundle.putString("url", "file:///$SD_WEB_PATH/language/language_smart.html")
        bundle.putString("url", "file:///android_asset/language/language-two.html")
        bundle.putString("title", "用户协议")
        intent.putExtras(bundle)
        view.context.startActivity(intent)

    }


    fun onMultiTest(view: View) {
        toast("${view.id} xxxx")

//            val intent = Intent(view.context, ListActivityDemo::class.java)
//            val intent = Intent(view.context, FileImportActivity::class.java)
        val intent = Intent(view.context, ExaminationSystemLoginActivity::class.java)
        view.context.startActivity(intent)
    }

    fun toLanguageWise(view: View) {
        val intent = Intent(view.context, WebViewActivity::class.java)
        val bundle = Bundle()
//        bundle.putString("url", "file:///$SD_WEB_PATH/language/language_smart.html")
        bundle.putString("url", "file:///android_asset/language/language-two.html")
        bundle.putString("title", "用户协议")
        intent.putExtras(bundle)
        view.context.startActivity(intent)
    }


    fun onMathLogic(view: View) {
        val intent = Intent(view.context, WebViewActivity::class.java)
        val bundle = Bundle()
//        bundle.putString("url", "file:///$SD_WEB_PATH/language/language_smart.html")
        bundle.putString("url", "file:///android_asset/language/language-two.html")
        bundle.putString("title", "用户协议")
        intent.putExtras(bundle)
        view.context.startActivity(intent)
    }


    fun toCommunication(view: View) {
        val intent = Intent(view.context, WebViewActivity::class.java)
        val bundle = Bundle()
//        bundle.putString("url", "file:///$SD_WEB_PATH/language/language_smart.html")
        bundle.putString("url", "file:///android_asset/language/language-two.html")
        bundle.putString("title", "用户协议")
        intent.putExtras(bundle)
        view.context.startActivity(intent)
    }


    fun toVision(view: View) {
        val intent = Intent(view.context, WebViewActivity::class.java)
        val bundle = Bundle()
        bundle.putString("url", "file:///android_asset/language/language-two.html")
        bundle.putString("title", "用户协议")
        intent.putExtras(bundle)
        view.context.startActivity(intent)
    }


    fun toMusic(view: View) {
        val intent = Intent(view.context, WebViewActivity::class.java)
        val bundle = Bundle()
        bundle.putString("url", "file:///android_asset/language/language-two.html")
        bundle.putString("title", "用户协议")
        intent.putExtras(bundle)
        view.context.startActivity(intent)
    }


    fun toSelfAware(view: View) {
        val intent = Intent(view.context, WebViewActivity::class.java)
        val bundle = Bundle()
        bundle.putString("url", "file:///android_asset/language/language-two.html")
        bundle.putString("title", "用户协议")
        intent.putExtras(bundle)
        view.context.startActivity(intent)
    }


    fun toSports(view: View) {
        val intent = Intent(view.context, WebViewActivity::class.java)
        val bundle = Bundle()
        bundle.putString("url", "file:///android_asset/language/language-two.html")
        bundle.putString("title", "用户协议")
        intent.putExtras(bundle)
        view.context.startActivity(intent)
    }


    fun toNatureObserve(view: View) {
        val intent = Intent(view.context, WebViewActivity::class.java)
        val bundle = Bundle()
//        bundle.putString("url", "file:///$SD_WEB_PATH/language/language_smart.html")
        bundle.putString("url", "file:///android_asset/language/language-two.html")
        bundle.putString("title", "用户协议")
        intent.putExtras(bundle)
        view.context.startActivity(intent)
    }

}
