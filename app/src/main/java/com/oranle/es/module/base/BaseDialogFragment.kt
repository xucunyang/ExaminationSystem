package com.oranle.es.module.base

import android.app.Activity
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.oranle.es.R

/**
 * @Description:
 * @author: Created by martin on 2017/3/23.
 */
abstract class BaseDialogFragment<ViewBinding : ViewDataBinding> : DialogFragment() {

    protected lateinit var dataBinding: ViewBinding

    @get:LayoutRes
    abstract val layoutId: Int

    var isViewDataBinding = true

    private lateinit var mContext: BaseActivity<ViewDataBinding>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NO_FRAME, R.style.dialogStyle)

        mContext = activity as BaseActivity<ViewDataBinding>

        if (activity != null && this.activity!!.isFinishing) {
            hideSoftInput(activity!!)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (isViewDataBinding) {
            dataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
            dataBinding.setLifecycleOwner(viewLifecycleOwner)
            initView()
            return dataBinding?.root
        } else {
            val view = super.onCreateView(inflater, container, savedInstanceState)
            initView()
            return view
        }
    }

    override fun onStart() {
        super.onStart()
        initWindow()
    }

    private fun initWindow() {
        val dialogWindow = dialog.window
        dialogWindow!!.setGravity(Gravity.CENTER)
        val lp = dialogWindow.attributes
        lp.width = 1024
        lp.height = 700
        dialogWindow.attributes = lp
    }


    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (activity != null && this.activity!!.isFinishing && hidden) {
            hideSoftInput(activity!!)
        }
    }

    abstract fun initView()

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
        return false
    }

    private fun hideSoftInput(activity: Activity) {
        val inputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(activity.currentFocus?.getWindowToken(), 0);
    }

    protected inline fun <reified T : BaseViewModel> getViewModel() =
        ViewModelProviders.of(this).get(T::class.java)

}