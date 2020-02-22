package com.oranle.es.data.api

import com.oranle.es.module.upgrade.DownloadService

object ServiceApi {

    inline fun <reified T> getApi(): T {
        return ApiClient.createApi(T::class.java)
    }

//    fun loginApi() = getApi<Login>()

    fun checkUpgradeApi() = getApi<CheckUpgrade>()

    fun getUpgradeLog() = getApi<UpgradeLog>()

    fun downloadFileService() = getApi<DownloadService>()

}
