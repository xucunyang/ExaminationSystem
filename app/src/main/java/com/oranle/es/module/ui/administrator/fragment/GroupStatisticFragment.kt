package com.oranle.es.module.ui.administrator.fragment

import android.view.View
import android.widget.AdapterView
import com.oranle.es.R
import com.oranle.es.data.entity.Assessment
import com.oranle.es.data.entity.ClassEntity
import com.oranle.es.data.sp.SpUtil
import com.oranle.es.databinding.FragmentGroupBinding
import com.oranle.es.module.base.BaseFragment
import com.oranle.es.module.base.toast
import com.oranle.es.module.ui.administrator.viewmodel.GroupStatisticViewModel
import timber.log.Timber

class GroupStatisticFragment : BaseFragment<FragmentGroupBinding>() {

    lateinit var viewModel: GroupStatisticViewModel

    var currentClass: ClassEntity? = null
    var school: String? = null
    var currentAssessment: Assessment? = null

    override val layoutId: Int
        get() = R.layout.fragment_group

    override fun initView() {

        viewModel = getViewModel()

        dataBinding?.apply {
            vm = viewModel

            schoolSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.school.value?.apply {
                        school = get(position)
                    }
                }
            }

            classSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.classesInCharge.value?.apply {
                        currentClass = get(position)
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

            nextStep.setOnClickListener {
                if (school == null) {
                    toast("请选择学校")
                    return@setOnClickListener
                }

                if (currentClass == null) {
                    toast("请选择班级")
                    return@setOnClickListener
                }

                if (currentAssessment == null) {
                    toast("请选择量表")
                    return@setOnClickListener
                }

                if (activity == null || activity!!.isFinishing()) {
                    Timber.e("activity is null or isFinishing")
                    return@setOnClickListener
                }

                toReportFrag(currentAssessment!!)
            }
        }

        viewModel.loadChoiceSuit(SpUtil.instance.getCurrentUser()!!)
    }


    private fun toReportFrag(currentAssessment: Assessment) {
        fragmentManager?.apply {
            val transaction = beginTransaction()
            transaction.replace(
                R.id.frameLayout,
                ReportFragment.newInstance(currentAssessment), ""
            ).commit()
        }

    }

}