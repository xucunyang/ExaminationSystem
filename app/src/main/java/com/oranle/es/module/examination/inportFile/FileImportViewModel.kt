package com.oranle.es.module.examination.inportFile

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.View
import androidx.core.content.FileProvider
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oranle.es.BuildConfig
import com.oranle.es.app.SessionApp
import com.oranle.es.module.base.BaseViewModel
import com.oranle.es.module.base.IO
import com.oranle.es.util.HWPFDocumentUtils
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File

const val FILE_REQUEST_CODE = 100

class FileImportViewModel : BaseViewModel() {

    val loading = MutableLiveData(false)

    val text = MutableLiveData<String>("导入量表")


    fun saveAssessment(data: Uri?) {

        data?.apply {
            text.value = "文件：${this.path}, 正在解析，请稍后..."

            val realPath = getRealFilePath(SessionApp.instance!!.applicationContext, data)

            viewModelScope.launch(IO) {
                val msg = HWPFDocumentUtils().readDocAndSave(realPath!!)
                text.postValue(msg)

                Timber.d("readDoc end...")
            }

            loading.value = false
        }
        if(data == null){
            text.value = "文件uri错误，请重新选择！！"
        }

    }

    fun onFileImportClick(view: View) {

        Timber.d("directory directory")

        text.value = "正在导入doc文件"
        loading.value = true

        val activity = view.context as Activity
        val intent = Intent(Intent.ACTION_GET_CONTENT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION

            val externalFilesDir = activity.getExternalFilesDir(null)
            val file = File(externalFilesDir, "es-web/")

            val uri = FileProvider.getUriForFile(
                activity,
                "${BuildConfig.APPLICATION_ID}.fileprovider", file
            )
            Timber.d("uri::$uri")
            intent.setDataAndType(uri, "file/*.doc")
        } else {
            intent.setDataAndType(Uri.fromFile(File("sdcard/es-web/")), "file/*.doc")
        }
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        activity.startActivityForResult(intent, FILE_REQUEST_CODE)
    }

    /**
     * 获取真实路径
     *
     * @param context
     * @param uri
     * @return
     */
    private fun getRealFilePath(context: Context, uri: Uri?): String? {
        if (null == uri)
            return null
        val scheme = uri.scheme
        var data: String? = null
        if (scheme == null)
            data = uri.path
        else if (ContentResolver.SCHEME_FILE == scheme) {
            data = uri.path
        } else if (ContentResolver.SCHEME_CONTENT == scheme) {
            val cursor = context.contentResolver.query(
                uri,
                arrayOf(MediaStore.Images.ImageColumns.DATA),
                null,
                null,
                null
            )
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    val index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                    if (index > -1) {
                        data = cursor.getString(index)
                    }
                }
                cursor.close()
            }
        }
        return data
    }

}