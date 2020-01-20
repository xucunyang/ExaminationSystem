package com.oranle.es.module.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.oranle.es.R
import com.oranle.es.databinding.ActivityMainBinding
import com.oranle.es.module.base.BaseActivity
import com.oranle.es.module.base.WebViewActivity
import com.oranle.es.module.base.toast
import com.oranle.es.module.ui.innovation.InnovationActivity
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
        val intent = Intent(view.context, InnovationActivity::class.java)
        view.context.startActivity(intent)
    }

    fun onMultiTest(view: View) {
        val intent = Intent(view.context, ExaminationSystemLoginActivity::class.java)
        view.context.startActivity(intent)
    }

    fun toLanguageWise(view: View) {
        val intent = Intent(view.context, WebViewActivity::class.java)
        val bundle = Bundle()
        bundle.putString("url", "file:///android_asset/language/language-one.html")
        bundle.putString("title", "用户协议")
        intent.putExtras(bundle)
        view.context.startActivity(intent)
    }

    fun onMathLogic(view: View) {
        val intent = Intent(view.context, WebViewActivity::class.java)
        val bundle = Bundle()
        // E:\code\ExaminationSystem\app\src\main\assets\mathematical\mathematical-one.html
        bundle.putString("url", "file:///android_asset/mathematical/mathematical-one.html")
        bundle.putString("title", "用户协议")
        intent.putExtras(bundle)
        view.context.startActivity(intent)
    }

    fun toCommunication(view: View) {
        val intent = Intent(view.context, WebViewActivity::class.java)
        val bundle = Bundle()
        // E:\code\ExaminationSystem\app\src\main\assets\interpersonal\interpersonal-one.html
        bundle.putString("url", "file:///android_asset/interpersonal/interpersonal-one.html")
        bundle.putString("title", "用户协议")
        intent.putExtras(bundle)
        view.context.startActivity(intent)
    }

    fun toVision(view: View) {
        val intent = Intent(view.context, WebViewActivity::class.java)
        val bundle = Bundle()
        //E:\code\ExaminationSystem\app\src\main\assets\visual\visual1.html
        bundle.putString("url", "file:///android_asset/visual/visual1.html")
        bundle.putString("title", "用户协议")
        intent.putExtras(bundle)
        view.context.startActivity(intent)
    }


    fun toMusic(view: View) {
        val intent = Intent(view.context, WebViewActivity::class.java)
        val bundle = Bundle()
        // E:\code\ExaminationSystem\app\src\main\assets\music\music-four.html
        bundle.putString("url", "file:///android_asset/music/music-one.html")
        bundle.putString("title", "用户协议")
        intent.putExtras(bundle)
        view.context.startActivity(intent)
    }

    fun toSelfAware(view: View) {
        val intent = Intent(view.context, WebViewActivity::class.java)
        val bundle = Bundle()
        // E:\code\ExaminationSystem\app\src\main\assets\ego\ego-one.html
        bundle.putString("url", "file:///android_asset/ego/ego-one.html")
        bundle.putString("title", "用户协议")
        intent.putExtras(bundle)
        view.context.startActivity(intent)
    }

    fun toSports(view: View) {
        val intent = Intent(view.context, WebViewActivity::class.java)
        val bundle = Bundle()
        // E:\code\ExaminationSystem\app\src\main\assets\movement\movement-one.html
        bundle.putString("url", "file:///android_asset/movement/movement-one.html")
        bundle.putString("title", "用户协议")
        intent.putExtras(bundle)
        view.context.startActivity(intent)
    }


    fun toNatureObserve(view: View) {
        val intent = Intent(view.context, WebViewActivity::class.java)
        val bundle = Bundle()
        // E:\code\ExaminationSystem\app\src\main\assets\observe\observe.html
//        bundle.putString("url", "file:///$SD_WEB_PATH/language/language_smart.html")
        bundle.putString("url", "file:///android_asset/observe/observe.html")
        bundle.putString("title", "用户协议")
        intent.putExtras(bundle)
        view.context.startActivity(intent)
    }

}
