package com.oranle.es.module.upgrade

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.core.content.FileProvider
import androidx.fragment.app.FragmentManager
import com.oranle.es.BuildConfig
import com.oranle.es.app.SessionApp
import com.oranle.es.data.api.ROOT_PATH
import com.oranle.es.data.api.ServiceApi
import com.oranle.es.module.base.CommDialog
import com.oranle.es.module.base.launchWrapped
import com.oranle.es.module.base.log
import com.oranle.es.module.base.toast
import com.oranle.es.util.FileUtil
import java.io.*


class UpgradeUtil(val fragmentManager: FragmentManager) {

    private val tipDialog: CommDialog = CommDialog().apply {
        setTitle("版本升级")
    }

    fun checkUpgrade() {

        downloadConfig()

//        launchWrapped(
//            asyncBlock = {
//                ServiceApi.checkUpgradeApi().checkAsync().await()
//            },
//            uiBlock = { apkNameInfo ->
//                if (!apkNameInfo.isNullOrBlank()) {
////                    if (apkNameInfo.endsWith(".apk")) {
////                        val startIndex = apkNameInfo.indexOf(".apk")
////                        val versionNum = apkNameInfo.substring(startIndex, apkNameInfo.length - 1)
////                        log("apkNameInfo $versionNum")
//
//                    // TODO 保存已下载的apk版本信息
//                    val serverApkVersion: Int
//                    serverApkVersion = try {
//                        apkNameInfo.toInt()
//                    } catch (e: NumberFormatException) {
//                        e.printStackTrace()
//                        log("$e")
//                        1
//                    }
//
//                    if (serverApkVersion > BuildConfig.VERSION_CODE) {
//                        showUpgradeTip(apkNameInfo)
//                        log(
//                            "upgrade,server Apk Version: $serverApkVersion, " +
//                                    "current version ${BuildConfig.VERSION_CODE}"
//                        )
//                    } else {
//                        log(
//                            "do not need upgrade,server Apk Version: $serverApkVersion, " +
//                                    "current version ${BuildConfig.VERSION_CODE}"
//                        )
//                    }
////                    }
//                    /*else {
//                            log("not end with '.apk' $apkNameInfo")
//                        }*/
//                }
//            }
//        )
    }

    /**
     *  提示是否升级
     */
    private fun showUpgradeTip(apkNameInfo: String) {

//        launchWrapped(
//            asyncBlock = {
//                ServiceApi.getUpgradeLog().getLogAsync().await()
//            },
//            uiBlock = {
//                if (!it.isNullOrBlank()) {
//                    val sb = StringBuilder()
//                    sb.append(it)
//                    sb.append(System.lineSeparator())
//                    sb.append("是否升级")
//                    tipDialog.setContent(it)
//                    tipDialog.setOKListener { flag ->
//                        if (flag) downloadApk(apkNameInfo)
//                    }
//                    tipDialog.show(fragmentManager, "")
//                }
//            })
    }

    private fun downloadApk(apkNameInfo: String) {

        val upgradeDialog = UpgradeDialog()
        upgradeDialog.show(fragmentManager, "")

        val externalFileDir = SessionApp.instance!!.getExternalFilesDir(null)
        if (externalFileDir == null) {
            log("getExternalFilesDir is null")
            return
        }
        val apkDestPath = "${externalFileDir.path}/${apkNameInfo}.apk"
        DownloadUtil.download(
            url = "$ROOT_PATH$apkNameInfo.apk",
            path = apkDestPath,
            downloadListener = object : DownloadListener {
                override fun onFinish(path: String?) {
                    updateUI {
                        upgradeDialog.setContent("开始安装...")
                        updateUiDelay(
                            {
                                upgradeDialog.dismiss()

                                installApk(
                                    SessionApp.instance!!.applicationContext,
                                    File(apkDestPath)
                                )
                            }, 500L
                        )
                    }
                }

                override fun onFail(errorInfo: String?) {
                    updateUI {
                        toast("下载失败")
                        upgradeDialog.dismiss()

                        val file = File(apkDestPath)
                        if (file.exists()) {
                            val delete = file.delete()
                            if (delete) {
                                log("delete for useless")
                            }
                        }
                    }
                }

                override fun onProgress(progress: Int) {
                    updateUI {
                        upgradeDialog.setProgress(progress)
                        upgradeDialog.setContent("${progress}%，下载中...")
                    }
                }

                override fun onStart() {
                    upgradeDialog.setContent("下载中...")
                }
            })

        upgradeDialog.setOKListener {
            if (!it) {
                DownloadUtil.cancelDownload()
            }
        }
    }

    private fun downloadConfig() {

        val externalFileDir = SessionApp.instance!!.getExternalFilesDir(null)
        if (externalFileDir == null) {
            log("getExternalFilesDir is null")
            return
        }
        val configPath = "${externalFileDir.path}/config"
        DownloadUtil.download(
            url = "${ROOT_PATH}config",
            path = configPath,
            downloadListener = object : DownloadListener {
                override fun onFinish(path: String?) {

                    val json = FileUtil.readFileContent(configPath)
                    log("on finish read file $json")

                    updateUI {

                    }
                }

                override fun onFail(errorInfo: String?) {
                    updateUI {
                        toast("下载失败")
                        deleteConfigFile()
                    }
                }

                private fun deleteConfigFile() {
                    val file = File(configPath)
                    if (file.exists()) {
                        val delete = file.delete()
                        if (delete) {
                            log("delete for useless")
                        }
                    }
                }

                override fun onProgress(progress: Int) {
                    updateUI {
                        log("onProgress $progress")
                    }
                }

                override fun onStart() {
//                    deleteConfigFile()
                }
            })

    }

    private fun updateUI(block: () -> Unit) {
        updateUiDelay(block, 0)
    }

    val uiHandler = Handler(Looper.getMainLooper())

    private fun updateUiDelay(block: () -> Unit, delay: Long = 0L) {
        uiHandler.postDelayed(Runnable(block), delay)
    }

    /**
     * 安装APK
     */
    fun installApk(context: Context, apk: File?) {
        if (apk == null) {
            return
        }
        // 1.修改文件权限
        setPermission(apk.getAbsolutePath())
        // 2. 安装APK
        val intent = Intent(Intent.ACTION_VIEW)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        val type = "application/vnd.android.package-archive"
        if (Build.VERSION.SDK_INT >= 24) {
            val uriForFile: Uri = FileProvider.getUriForFile(
                context, "${context.packageName}.fileprovider",
                apk
            )
            intent.setDataAndType(uriForFile, type)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        } else {
            intent.setDataAndType(Uri.fromFile(apk), type)
        }
        context.startActivity(intent)
    }

    /**
     * 修改文件权限
     */
    private fun setPermission(absolutePath: String) {
        try {
            val command = "chmod 777 $absolutePath"
            val runtime = Runtime.getRuntime()
            runtime.exec(command)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }
}