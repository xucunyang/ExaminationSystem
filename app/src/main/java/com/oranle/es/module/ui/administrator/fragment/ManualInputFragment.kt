package com.oranle.es.module.ui.administrator.fragment

import com.oranle.es.R
import com.oranle.es.data.sp.SpUtil
import com.oranle.es.databinding.FragmentEntryBinding
import com.oranle.es.module.base.BaseFragment
import com.oranle.es.module.ui.administrator.viewmodel.ManualInputViewModel

class ManualInputFragment : BaseFragment<FragmentEntryBinding>() {

    lateinit var viewModel: ManualInputViewModel

    override val layoutId: Int
        get() = R.layout.fragment_entry

    override fun initView() {

        viewModel = getViewModel()

        dataBinding?.apply {
            vm = viewModel
        }

        viewModel.loadStudentByManagerId(SpUtil.instance.getCurrentUser()!!)

    }

}