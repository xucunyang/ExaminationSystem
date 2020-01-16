package com.oranle.es.module.ui.administrator.fragment

import com.oranle.es.R
import com.oranle.es.data.sp.SpUtil
import com.oranle.es.databinding.FragmentPersonStatisticsBinding
import com.oranle.es.module.base.BaseFragment
import com.oranle.es.module.ui.administrator.viewmodel.GroupStatisticViewModel

class PersonalStatisticFragment : BaseFragment<FragmentPersonStatisticsBinding>() {

    lateinit var viewModel: GroupStatisticViewModel

    override val layoutId: Int
        get() = R.layout.fragment_person_statistics

    override fun initView() {

        viewModel = getViewModel()

        dataBinding?.apply {
            vm = viewModel
        }

        viewModel.loadChoiceSuit(SpUtil.instance.getCurrentUser()!!)
    }

}