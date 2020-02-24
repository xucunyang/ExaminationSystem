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
import com.oranle.es.module.base.CommDialog
import com.oranle.es.module.base.log
import com.oranle.es.module.base.toast
import com.oranle.es.util.FileUtil
import org.json.JSONException
import org.json.JSONObject
import java.io.File


class UpgradeUtil(val fragmentManager: FragmentManager) {

    private val tipDialog: CommDialog = CommDialog().apply {
        setTitle("版本升级")
    }

    fun checkUpgrade() {

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
                    var versionInfo: String? = null
                    var msgInfo: String? = null
                    try {
                        val jsonObject = JSONObject(json)
                        versionInfo = getStrFromJson(jsonObject, "version")
                        msgInfo = getStrFromJson(jsonObject, "msg")
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        log("$e")
                    }

                    if (!versionInfo.isNullOrBlank()) {
                        if (versionInfo.endsWith(".apk")) {
                            val endIndex = versionInfo.indexOf(".apk")
                            val versionNum =
                                versionInfo.substring(0, endIndex)

                            log("apkNameInfo $versionNum")

                            val serverApkVersion: Int
                            serverApkVersion = try {
                                versionNum.toInt()
                            } catch (e: NumberFormatException) {
                                e.printStackTrace()
                                log("$e")
                                1
                            }

                            if (serverApkVersion > BuildConfig.VERSION_CODE) {

                                val sb = StringBuilder()
                                sb.append(msgInfo)
                                sb.append(System.lineSeparator())
                                sb.append("是否升级")
                                tipDialog.setContent(sb.toString())
                                tipDialog.setOKListener { flag ->
                                    if (flag) downloadApk(versionInfo)
                                }
                                tipDialog.show(fragmentManager, "")

                                log(
                                    "upgrade,server Apk Version: $serverApkVersion, " +
                                            "current version ${BuildConfig.VERSION_CODE}"
                                )
                            } else {
                                log(
                                    "do not need upgrade,server Apk Version: $serverApkVersion, " +
                                            "current version ${BuildConfig.VERSION_CODE}"
                                )
                            }
                        }
                        /*else {
                                log("not end with '.apk' $apkNameInfo")
                            }*/
                    }

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

                override fun onProgress(progress: Int, current: Long, total: Long) {
                    updateUI {
                        log("onProgress $progress, current:$current, total:$total")
                    }
                }

                override fun onStart() {
//                    deleteConfigFile()
                }
            })
    }

    private fun getStrFromJson(jsonObj: JSONObject, key: String): String? {
        var value: String? = null
        try {
            value = jsonObj.optString(key)
        } catch (e: Exception) {
            e.printStackTrace()
            log("$e")
        }
        return value
    }

    private fun downloadApk(apkNameInfo: String) {

        val upgradeDialog = UpgradeDialog()
        upgradeDialog.show(fragmentManager, "")

        val externalFileDir = SessionApp.instance!!.getExternalFilesDir(null)
        if (externalFileDir == null) {
            log("getExternalFilesDir is null")
            return
        }

        val apkDestPath = "${externalFileDir.path}/${apkNameInfo}"

        DownloadUtil.download(
            url = "$ROOT_PATH$apkNameInfo",
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

                override fun onProgress(progress: Int, current: Long, total: Long) {
                    updateUI {
                        upgradeDialog.setProgress(progress)

                        upgradeDialog.setContent("${progress}%，下载中...\n${current/1024}k/${total/1024}k")
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