package com.oranle.es.module.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.oranle.es.R
import com.oranle.es.databinding.ActivityMainBinding
import com.oranle.es.module.base.BaseActivity
import com.oranle.es.module.base.WebViewActivity
import com.oranle.es.module.base.launchWrapped
import com.oranle.es.module.base.log
import com.oranle.es.module.ui.login.ExaminationSystemLoginActivity
import com.oranle.es.module.upgrade.DownloadListener
import com.oranle.es.module.upgrade.DownloadUtil
import com.oranle.es.module.upgrade.UpgradeUtil
import com.oranle.es.util.GlideUtil
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

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
//        val intent = Intent(view.context, InnovationActivity::class.java)
//        view.context.startActivity(intent)

        GlideUtil.loadImage(this, "http://47.99.148.238/edu/11.jpg", iv)

        launchWrapped(
            lifecycleOwner = this,
            asyncBlock = {
                Timber.w("final block")
//                ServiceApi.downloadApk().downloadAsync().await()

                val externalPath = this@HomeActivity.getExternalFilesDir(null)?.path
                if (externalPath == null) {
                    log("externalPath is null")
                    return@launchWrapped
                }
                log("externalPath $externalPath")
                DownloadUtil.download(
                    "http://47.99.148.238/edu/1.apk",
                    externalPath + "/1.apk",
                    object : DownloadListener {
                        override fun onFinish(path: String?) {
                            log(" onFinish $path ")
                        }

                        override fun onFail(errorInfo: String?) {
                            log(" onFail $errorInfo ")
                        }

                        override fun onProgress(progress: Int) {
                            log(" onProgress $progress ")
                        }

                        override fun onStart() {
                            log(" onStart ")
                        }

                    }
                    )
            },
            uiBlock = { result ->
                log("ui block $result")
            },
            exceptionHandler = {
                it.printStackTrace()
                Timber.w("xxx excetiopn $it")
            },
            finalBlock = {
                Timber.w("final block")
            })



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

}
