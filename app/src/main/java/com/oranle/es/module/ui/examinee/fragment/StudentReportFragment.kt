package com.oranle.es.module.ui.examinee.fragment

import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.oranle.es.R
import com.oranle.es.databinding.FragmentStudentReportBinding
import com.oranle.es.module.base.BaseFragment
import com.oranle.es.module.ui.administrator.viewmodel.StatisticViewModel
import com.oranle.es.module.ui.examinee.StudentReportAdapter

class StudentReportFragment : BaseFragment<FragmentStudentReportBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_student_report

    private lateinit var mViewModel: StatisticViewModel

    override fun initView() {
        mViewModel = getViewModel()

        val studentReportAdapter = StudentReportAdapter(mViewModel)

        dataBinding?.apply {
            vm = mViewModel
            recyclerView.adapter = studentReportAdapter
            recyclerView.layoutManager = LinearLayoutManager(activity)
            recyclerView.addItemDecoration(
                DividerItemDecoration(
                    activity,
                    DividerItemDecoration.VERTICAL
                )
            )

            studentReportAdapter.addHeader(
                DataBindingUtil.inflate(
                    LayoutInflater.from(activity),
                    R.layout.header_examinee_report,
                    recyclerView, false
                )
            )
        }

        mViewModel.loadReport(null, null)
    }

}