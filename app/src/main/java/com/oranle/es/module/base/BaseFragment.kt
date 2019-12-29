package com.oranle.es.module.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders

/**
 * @Description:
 * @author: Created by martin on 2017/3/23.
 */
abstract class BaseFragment<ViewBinding : ViewDataBinding> : Fragment() {

    protected var dataBinding: ViewBinding? = null

    @get:LayoutRes
    abstract val layoutId: Int

    var isViewDataBinding = true

    private var mContext: BaseActivity<ViewBinding>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity as BaseActivity<ViewBinding>
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (isViewDataBinding) {
            dataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
            dataBinding?.setLifecycleOwner(viewLifecycleOwner)
            initView()
            return dataBinding?.root
        } else {
            val view = super.onCreateView(inflater, container, savedInstanceState)
            initView()
            return view
        }
    }

    abstract fun initView()

    protected inline fun <reified T : BaseViewModel> getViewModel() =
        ViewModelProviders.of(this).get(T::class.java)

}