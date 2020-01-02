package com.oranle.es.module.base

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.LayoutRes
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import timber.log.Timber


abstract class BaseActivity<ViewBinding : ViewDataBinding> :
    FragmentActivity() {

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

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {

        if (ev?.getAction() == MotionEvent.ACTION_DOWN) {
            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点） var
            val v = currentFocus;
            if (isShouldHideInput(v, ev)) {
                hideSoftInput(this);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private fun isShouldHideInput(v: View?, event: MotionEvent): Boolean {
        if (v != null && v is EditText) {
            val l = intArrayOf(0, 0)
            v.getLocationInWindow(l)
            val left = l[0]
            val top = l[1]
            val bottom = top + v.getHeight()
            val right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom)
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    private fun hideSoftInput(activity: Activity) {
        val inputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(activity.currentFocus?.getWindowToken(), 0);
    }
}