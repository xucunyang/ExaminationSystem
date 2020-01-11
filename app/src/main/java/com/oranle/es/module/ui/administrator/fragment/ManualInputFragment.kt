package com.oranle.es.module.ui.administrator.fragment

import android.view.View
import android.widget.AdapterView
import com.oranle.es.R
import com.oranle.es.data.entity.Assessment
import com.oranle.es.data.entity.User
import com.oranle.es.data.sp.SpUtil
import com.oranle.es.databinding.FragmentEntryBinding
import com.oranle.es.module.base.BaseFragment
import com.oranle.es.module.base.toast
import com.oranle.es.module.examination.ExamDetailDialog
import com.oranle.es.module.ui.administrator.viewmodel.ManualInputViewModel

class ManualInputFragment : BaseFragment<FragmentEntryBinding>() {

    lateinit var viewModel: ManualInputViewModel

    var currentStudent: User? = null
    var currentAssessment: Assessment? = null

    override val layoutId: Int
        get() = R.layout.fragment_entry

    override fun initView() {

        viewModel = getViewModel()

        dataBinding?.apply {
            vm = viewModel

            studentSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.students.value?.apply {
                        currentStudent = get(position)
                    }
                }
            }

            assessmentSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.assessments.value?.apply {
                        currentAssessment = get(position)
                    }
                }

            }

            nextStep.setOnClickListener{
                if (currentStudent == null) {
                    toast("请选择测评用户")
                    return@setOnClickListener
                }
                if (currentAssessment == null) {
                    toast("请选择量表")
                    return@setOnClickListener
                }

                if (activity == null || activity!!.isFinishing()) {
                    toast("请选择量表")
                    return@setOnClickListener
                }

                val dialog = ExamDetailDialog(activity!!, currentAssessment!!)
                dialog.show(activity!!.supportFragmentManager, "")
            }
        }

        viewModel.loadStudentByManagerId(SpUtil.instance.getCurrentUser()!!)

    }

}