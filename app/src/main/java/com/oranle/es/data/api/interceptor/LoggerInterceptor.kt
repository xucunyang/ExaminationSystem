package com.oranle.es.data.api.interceptor

import android.text.TextUtils
import com.oranle.es.BuildConfig
import okhttp3.*
import okio.Buffer
import timber.log.Timber
import java.io.IOException

class LoggerInterceptor : Interceptor {

    private val showLog = BuildConfig.DEBUG

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response? {
        if (showLog) {
            val request = chain.request()
            logForRequest(request)
            val response = chain.proceed(request)
            return logForResponse(response)
        } else {
            return chain.proceed(chain.request())
        }
    }

    private fun logForResponse(response: Response): Response? {
        try {
            //===>response log
            Timber.e("========response'log=======")
            val builder = response.newBuilder()
            val clone = builder.build()
            Timber.e("url : %s", clone.request().url())
            Timber.e("code : %s", clone.code())
            Timber.e("protocol : %s", clone.protocol())
            if (!TextUtils.isEmpty(clone.message()))
                Timber.e("message : %s", clone.message())

            var body = clone.body()
            if (body != null) {
                val mediaType = body.contentType()
                if (mediaType != null) {
                    Timber.e("responseBody's contentType : $mediaType")
                    if (isText(mediaType)) {
                        val resp = body.string()
                        Timber.e("responseBody's content : $resp")

                        body = ResponseBody.create(mediaType, resp)
                        return response.newBuilder().body(body).build()
                    } else {
                        Timber.e("responseBody's content :  maybe [file part] , too large too print , ignored!")
                    }
                }
            }
            Timber.e("========response'log=======end")
        } catch (e: Exception) {
            e.printStackTrace();
        }

        return response
    }

    private fun logForRequest(request: Request) {
        try {
            val url = request.url().toString()
            val headers = request.headers()

            Timber.e("========request'log=======")

            Timber.e("method : ${request.method()}")
            Timber.e("url : $url")
            if (headers != null && headers.size() > 0) {
                Timber.e("headers : $headers")
            }
            val requestBody = request.body()
            if (requestBody != null) {
                val mediaType = requestBody.contentType()
                if (mediaType != null) {
                    Timber.e("requestBody's contentType : $mediaType")
                    if (isText(mediaType)) {
                        Timber.e("requestBody's content : ${bodyToString(request)}")
                    } else {
                        Timber.e("requestBody's content :  maybe [file part] , too large too print , ignored!")
                    }
                }
            }
            Timber.e("========request'log=======end")
        } catch (e: Exception) {
            //            e.printStackTrace();
        }

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

    private fun bodyToString(request: Request): String {
        try {
            val copy = request.newBuilder().build()
            val buffer = Buffer()
            copy.body()!!.writeTo(buffer)
            return buffer.readUtf8()
        } catch (e: IOException) {
            return "something error when show requestBody."
        }
    }
}

