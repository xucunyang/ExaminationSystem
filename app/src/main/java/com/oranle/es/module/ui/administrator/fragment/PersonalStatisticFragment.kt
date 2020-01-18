package com.oranle.es.module.ui.administrator.fragment

import android.view.View
import android.widget.AdapterView
import com.oranle.es.R
import com.oranle.es.data.entity.Assessment
import com.oranle.es.data.entity.ClassEntity
import com.oranle.es.data.sp.SpUtil
import com.oranle.es.databinding.FragmentPersonStatisticsBinding
import com.oranle.es.module.base.BaseFragment
import com.oranle.es.module.ui.administrator.AdministratorActivity
import com.oranle.es.module.ui.administrator.viewmodel.GroupStatisticViewModel

class PersonalStatisticFragment : BaseFragment<FragmentPersonStatisticsBinding>() {

    lateinit var viewModel: GroupStatisticViewModel

    var school: String? = null
    var currentClass: ClassEntity? = null
    var currentAssessment: Assessment? = null

    override val layoutId: Int
        get() = R.layout.fragment_person_statistics

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
                val administratorActivity = activity as AdministratorActivity
                administratorActivity.onReport(it)
            }

        }

        viewModel.loadChoiceSuit(SpUtil.instance.getCurrentUser()!!)
    }

}