package com.oranle.es.module.base

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.oranle.es.R
import com.oranle.es.databinding.LayoutWebviewBinding
import kotlinx.android.synthetic.main.layout_webview.*
import timber.log.Timber

const val URL = "url"
const val TITLE = "title"

class WebViewActivity : BaseActivity<LayoutWebviewBinding>() {

    override val layoutId: Int
        get() = R.layout.layout_webview

    private val url by argument<String>(URL)
    val mTitle = ""

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding.apply {
            webView.settings.apply {
                javaScriptEnabled = true
                useWideViewPort = true
                loadWithOverviewMode = true
                allowFileAccess = true;
                allowContentAccess = true;
            }
            webView.webViewClient = (mWebViewClient)
            webView.webChromeClient = mWebChromeClient


            includeTitle.tvTitle?.text = mTitle
            webView.loadUrl(url)
        }

    }

    private val mWebViewClient = object : WebViewClient() {
        override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
            try {
                handler.proceed()  // 如果证书一致，忽略错误
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
//            this@WebViewActivity.showLoading()
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
//            this@WebViewActivity.hideLoading()
        }

        override fun onReceivedError(
            view: WebView,
            errorCode: Int,
            description: String,
            failingUrl: String
        ) {
            super.onReceivedError(view, errorCode, description, failingUrl)
//            this@WebViewActivity.hideLoading()
        }

    }
    private val mWebChromeClient = object : WebChromeClient() {

        override fun onReceivedTitle(view: WebView, title: String) {
            if (TextUtils.isEmpty(mTitle)) {
                if (!TextUtils.isEmpty(title) && title != view.url) {
                    dataBinding?.includeTitle?.tvTitle?.text = mTitle
                }
            }
        }

        override fun onProgressChanged(view: WebView, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        Timber.d("onKeyDown ${webView.canGoBack()}")

        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event);//退出H5界面
    }

}