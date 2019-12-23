package com.oranle.es.module.ui.administrator.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.oranle.es.R
import com.oranle.es.databinding.FragmentPwdBinding
import com.oranle.es.module.base.BaseFragment

class AdminPwdFragment : BaseFragment<FragmentPwdBinding>() {
    override fun initView() {
    }

    override val layoutId: Int
        get() = R.layout.fragment_pwd

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}
