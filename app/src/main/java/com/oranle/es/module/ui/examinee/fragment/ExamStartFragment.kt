package com.oranle.es.module.ui.examinee.fragment

import android.os.Bundle
import com.oranle.es.R
import com.oranle.es.data.entity.Assessment
import com.oranle.es.databinding.FragmentExamStartBinding
import com.oranle.es.module.base.BaseFragment
import com.oranle.es.module.ui.examinee.viewmodel.AssessmentSelectViewModel

class ExamStartFragment : BaseFragment<FragmentExamStartBinding>() {

    companion object {
        fun newInstance(assessment: Assessment): ExamStartFragment {
            val bundle = Bundle()
            bundle.putSerializable("assessment", assessment)

            val fragment = ExamStartFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    lateinit var viewModel: AssessmentSelectViewModel

    override val layoutId: Int
        get() = R.layout.fragment_exam_start

    override fun initView() {
        viewModel = getViewModel()

        val assessment = arguments?.getSerializable("assessment") as? Assessment

        assessment?.let { assessmentTemp ->
            dataBinding?.apply {
                vm = viewModel
                asInLayout = assessmentTemp
            }
        }

    }
}