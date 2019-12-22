package com.oranle.es.module.base

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import timber.log.Timber


abstract class BaseActivity<ViewBinding : ViewDataBinding> :
    AppCompatActivity() {

    private val PERMISSON_REQ_CODE = 100

    @get:LayoutRes
    abstract val layoutId: Int

    lateinit var dataBinding: ViewBinding

    private val progressUtil = ProgressUtil()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this, layoutId)
        dataBinding.setLifecycleOwner(this)

        checkPermission()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        var allGranted = true
        val ungrantedPermission = mutableListOf<String>()
        if (requestCode == PERMISSON_REQ_CODE) {
            for ((index, r) in grantResults.withIndex()) {
                if (r != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false
                    ungrantedPermission.add(permissions[index])
                    break
                }
            }
            if (!allGranted) {
                toast("${ungrantedPermission} 权限未获得，请手动开启")
            }
        }
    }


    private fun checkPermission() {
        var targetSdkVersion = 0

        try {
            val info = this.packageManager.getPackageInfo(this.packageName, 0)
            targetSdkVersion = info.applicationInfo.targetSdkVersion//获取应用的Target版本
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            Timber.d("检查权限_err0");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (targetSdkVersion >= Build.VERSION_CODES.M) {
                if (!isAllPermissionGranted()) {
                    ActivityCompat.requestPermissions(this, permissions, PERMISSON_REQ_CODE)
                } else {
                    Timber.d("all permission granted")
                }
            }
        }
    }

    private val permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.INTERNET
    )

    private fun isAllPermissionGranted(): Boolean {
        for (p: String in permissions) {
            if (ContextCompat.checkSelfPermission(this, p) !=
                PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    protected fun initDialog(viewModel: BaseViewModel) {
        viewModel.showDialog.observe(this, Observer { value ->
            if (value)
                progressUtil.showLoading(this@BaseActivity, true, viewModel.content.value)
            else
                progressUtil.dismiss()
        })
    }

    protected inline fun <reified T : BaseViewModel> getViewModel() =
        ViewModelProviders.of(this).get(T::class.java)

}