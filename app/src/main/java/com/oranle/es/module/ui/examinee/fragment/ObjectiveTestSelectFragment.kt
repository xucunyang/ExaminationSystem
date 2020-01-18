package com.oranle.es.module.ui.examinee.fragment

import com.oranle.es.R
import com.oranle.es.databinding.FragmentSubjectiveSelectBinding
import com.oranle.es.module.base.BaseFragment
import com.oranle.es.module.ui.examinee.viewmodel.AssessmentSelectViewModel
import com.oranle.es.module.ui.examinee.viewmodel.AssessmentType

class ObjectiveTestSelectFragment : BaseFragment<FragmentSubjectiveSelectBinding>() {

    lateinit var viewModel : AssessmentSelectViewModel

    override val layoutId: Int
        get() = R.layout.fragment_subjective_select

    override fun initView() {

        viewModel = getViewModel()

        dataBinding?.apply {
            vm = viewModel
        }

        viewModel.loadAssessments(AssessmentType.OBJECTIVE)
    }

}