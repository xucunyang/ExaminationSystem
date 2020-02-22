package com.oranle.es.module.upgrade

import com.oranle.es.data.api.RELEASE_IP
import com.oranle.es.module.base.log
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.*
import java.util.concurrent.Executors


object DownloadUtil {

    private var call: Call<ResponseBody>? = null

    fun download(
        url: String?,
        path: String,
        downloadListener: DownloadListener
    ) {

        val retrofit = Retrofit.Builder()
            .baseUrl(RELEASE_IP) //通过线程池获取一个线程，指定callback在子线程中运行。
            .callbackExecutor(Executors.newSingleThreadExecutor())
            .build()

        val service = retrofit.create(DownloadService::class.java)
        call = service.download(url)

        call?.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                log("onResponse ${response.message()}")
                //将Response写入到从磁盘中，详见下面分析
                //注意，这个方法是运行在子线程中的
                writeResponseToDisk(path, response, downloadListener)
            }

            override fun onFailure(
                call: Call<ResponseBody>,
                throwable: Throwable
            ) {
                log("onFailure ${throwable.message}")
                downloadListener.onFail("网络错误～")
            }
        })
    }

    fun cancelDownload() {
        call?.cancel()
    }

    private fun writeResponseToDisk(
        path: String,
        response: Response<ResponseBody>,
        downloadListener: DownloadListener
    ) {
        //从response获取输入流以及总大小
        writeFileFromIS(
            File(path),
            response.body()!!.byteStream(),
            response.body()!!.contentLength(),
            downloadListener
        )
    }

    private const val sBufferSize = 8192
    //将输入流写入文件
    private fun writeFileFromIS(
        file: File,
        `is`: InputStream,
        totalLength: Long,
        downloadListener: DownloadListener
    ) { //开始下载
        downloadListener.onStart()
        //创建文件
        if (!file.exists()) {
            if (file.parentFile != null && !file.parentFile.exists()) {
                file.parentFile.mkdir()
            }
            try {
                file.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
                downloadListener.onFail("createNewFile IOException")
            }
        }
        var os: OutputStream? = null
        var currentLength: Long = 0
        try {
            os = BufferedOutputStream(FileOutputStream(file))
            val data = ByteArray(sBufferSize)
            var len: Int
            while (`is`.read(data, 0, sBufferSize).also { len = it } != -1) {
                os.write(data, 0, len)
                currentLength += len.toLong()
                //计算当前下载进度
                downloadListener.onProgress((100 * currentLength / totalLength).toInt())
            }
        } catch (e: IOException) {
            e.printStackTrace()
            downloadListener.onFail("IOException")
        } finally {
            try {
                `is`.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            try {
                os?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            //下载完成，并返回保存的文件路径
            if (currentLength == totalLength) {
                downloadListener.onFinish(file.absolutePath)
            }
        }
    }
}