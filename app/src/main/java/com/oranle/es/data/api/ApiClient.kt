package com.oranle.es.data.api

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.oranle.es.BuildConfig
import com.oranle.es.data.api.interceptor.LenientGsonConverterFactory
import com.oranle.es.data.api.interceptor.LoggerInterceptor
import com.oranle.es.data.api.interceptor.TokenRequestInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ApiClient {

    private val mRetrofitBuilder: Retrofit.Builder = Retrofit.Builder()

    private val mOkHttpClientBuilder: OkHttpClient.Builder

    private const val TIME_OUT = 30L

    init {
        mRetrofitBuilder.baseUrl(BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(LenientGsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .addConverterFactory(GsonConverterFactory.create())


        mOkHttpClientBuilder = OkHttpClient.Builder()
        mOkHttpClientBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        mOkHttpClientBuilder.readTimeout(TIME_OUT, TimeUnit.SECONDS)
        mOkHttpClientBuilder.addInterceptor(TokenRequestInterceptor())
        mOkHttpClientBuilder.addInterceptor { chain: Interceptor.Chain ->
            val origin = chain.request()
            val request = origin.newBuilder()
//                    .addHeader("token", SpUtils.getToken())
                .method(origin.method(), origin.body())
                .build()
            chain.proceed(request)
        }

        if (BuildConfig.DEBUG) {
            mOkHttpClientBuilder.addInterceptor(LoggerInterceptor())
        }
    }

    fun <T> createApi(clazz: Class<T>): T {
        return mRetrofitBuilder
            .client(mOkHttpClientBuilder.build())
            .build()
            .create(clazz)
    }

    fun <T> createApi(baseUrl: String, clazz: Class<T>): T {
        mRetrofitBuilder.baseUrl(baseUrl)
        return createApi(clazz)
    }
}