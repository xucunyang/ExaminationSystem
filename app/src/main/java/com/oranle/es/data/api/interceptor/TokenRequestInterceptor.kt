package com.oranle.es.data.api.interceptor

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.oranle.es.app.SessionApp
import com.oranle.es.module.base.toast
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody
import timber.log.Timber
import java.io.IOException

class TokenRequestInterceptor : Interceptor {


    private val TOKEN_INVALID = 4081
    private val TOKEN_EXPIRED = 300

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response? {
        val request = chain.request()
        val response = chain.proceed(request)

        try {
            val builder = response.newBuilder()
            val clone = builder.build()
            var body = clone.body()
            if (body != null) {
                val mediaType = body.contentType()
                if (mediaType != null) {
                    if (isText(mediaType)) {
                        val resp = body.string()
                        val code = parseJsonResultCode(resp, 0)
                        if (code == TOKEN_INVALID || code == TOKEN_EXPIRED) {
                            Timber.v("token invalid TOKEN_EXPIRED ")
                            exit()
                        }
                        body = ResponseBody.create(mediaType, resp)
                        return response.newBuilder().body(body).build()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return response
    }

    private fun isText(mediaType: MediaType): Boolean {
        if (mediaType.type() != null && mediaType.type() == "text" || mediaType.type() == "application") {
            return true
        }
        if (mediaType.subtype() != null) {
            if (mediaType.subtype() == "json" ||
                mediaType.subtype() == "xml" ||
                mediaType.subtype() == "html" ||
                mediaType.subtype() == "webviewhtml"
            )
                return true
        }
        return false
    }

    private fun parseJsonResultCode(msg: String?, def: Int): Int {
        if (msg == null || msg.length <= 0) {
            return def
        }
        val start = msg.indexOf("\"code\"")
        if (start < 0) {
            return def
        }
        val sb = StringBuilder()
        var contentStated = false
        for (i in start until msg.length) {
            val c = msg[i]
            if (contentStated) {
                if (c == '\"') {
                    continue
                } else if (c == ',' || c == '}') {
                    break
                }
                sb.append(c)
            } else if (c == ':') {
                contentStated = true
            }
        }
        try {
            return Integer.valueOf(sb.toString())
        } catch (e: NumberFormatException) {
            return def
        }

    }

    @Synchronized
    private fun exit() {


        Handler(Looper.getMainLooper()).post {
            Toast.makeText(SessionApp.instance, "登录过期，请重新登陆", Toast.LENGTH_SHORT).show()
        }

        toast("登录过期，请重新登陆")
    }


}
