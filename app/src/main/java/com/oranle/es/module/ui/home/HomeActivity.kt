package com.oranle.es.module.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import com.oranle.es.R
import com.oranle.es.databinding.ActivityMainBinding
import com.oranle.es.module.base.BaseActivity
import com.oranle.es.module.base.WebViewActivity
import com.oranle.es.module.ui.innovation.InnovationActivity
import com.oranle.es.module.ui.login.ExaminationSystemLoginActivity
import com.oranle.es.module.upgrade.UpgradeUtil
import kotlin.system.exitProcess


class HomeActivity : BaseActivity<ActivityMainBinding>() {

    override val layoutId: Int
        get() = R.layout.activity_main

    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = getViewModel()
        initDialog(viewModel)

        UpgradeUtil(supportFragmentManager).checkUpgrade()
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
        bundle.putString("url", "file:///android_asset/language/language-type.html")
        intent.putExtras(bundle)
        view.context.startActivity(intent)
    }

    fun onMathLogic(view: View) {
        val intent = Intent(view.context, WebViewActivity::class.java)
        val bundle = Bundle()
        bundle.putString("url", "file:///android_asset/mathematical/mathematical-type.html")
        intent.putExtras(bundle)
        view.context.startActivity(intent)
    }

    fun toCommunication(view: View) {
        val intent = Intent(view.context, WebViewActivity::class.java)
        val bundle = Bundle()
        bundle.putString("url", "file:///android_asset/interpersonal/interpersonal-type.html")
        intent.putExtras(bundle)
        view.context.startActivity(intent)
    }

    fun toVision(view: View) {
        val intent = Intent(view.context, WebViewActivity::class.java)
        val bundle = Bundle()
        bundle.putString("url", "file:///android_asset/visual/visual-type.html")
        intent.putExtras(bundle)
        view.context.startActivity(intent)
    }


    fun toMusic(view: View) {
        val intent = Intent(view.context, WebViewActivity::class.java)
        val bundle = Bundle()
        bundle.putString("url", "file:///android_asset/music/music-type.html")
        intent.putExtras(bundle)
        view.context.startActivity(intent)
    }

    fun toSelfAware(view: View) {
        val intent = Intent(view.context, WebViewActivity::class.java)
        val bundle = Bundle()
        bundle.putString("url", "file:///android_asset/ego/ego-type.html")
        intent.putExtras(bundle)
        view.context.startActivity(intent)
    }

    fun toSports(view: View) {
        val intent = Intent(view.context, WebViewActivity::class.java)
        val bundle = Bundle()
        bundle.putString("url", "file:///android_asset/movement/movement-type.html")
        intent.putExtras(bundle)
        view.context.startActivity(intent)
    }


    fun toNatureObserve(view: View) {
        val intent = Intent(view.context, WebViewActivity::class.java)
        val bundle = Bundle()
        bundle.putString("url", "file:///android_asset/natural/natural-type.html")
        intent.putExtras(bundle)
        view.context.startActivity(intent)
    }

    //两秒内按返回键两次退出程序
    private var exitTime: Long = 0

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() === KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(applicationContext, "再按一次退出程序", Toast.LENGTH_SHORT).show()
                exitTime = System.currentTimeMillis()
            } else {
                finish()
                exitProcess(0)
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}
