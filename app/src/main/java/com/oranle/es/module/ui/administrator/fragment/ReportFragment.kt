package com.oranle.es.module.ui.administrator.fragment

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oranle.es.R
import com.oranle.es.data.entity.Assessment
import com.oranle.es.data.entity.SheetReport
import com.oranle.es.databinding.FragmentReportBinding
import com.oranle.es.databinding.ItemReportBinding
import com.oranle.es.module.base.BaseAdapter
import com.oranle.es.module.base.BaseFragment
import com.oranle.es.module.ui.administrator.viewmodel.GroupStatisticViewModel

class ReportFragment : BaseFragment<FragmentReportBinding>() {

    companion object {
        fun newInstance(assessment: Assessment? = null): ReportFragment {
            val fragment = ReportFragment()
            assessment?.apply {
                val args = Bundle()
                args.putSerializable("assessment", assessment)
                fragment.arguments = args
            }
            return fragment
        }
    }

    lateinit var mViewModel: GroupStatisticViewModel

    override val layoutId: Int
        get() = R.layout.fragment_report

    override fun initView() {

        val assessment = arguments?.getSerializable("assessment") as? Assessment

        mViewModel = getViewModel()

        dataBinding?.apply {
            vm = mViewModel

            recyclerView.adapter = ReportAdapter(mViewModel)
            recyclerView.layoutManager = LinearLayoutManager(activity) as RecyclerView.LayoutManager?
        }

        assessment?.apply {
            mViewModel.loadReportByAssessment(assessment.id)
        }
    }

    inner class ReportAdapter(vm: GroupStatisticViewModel) :
        BaseAdapter<SheetReport, ItemReportBinding, GroupStatisticViewModel>(vm) {
        override fun doBindViewHolder(
            binding: ItemReportBinding,
            item: SheetReport,
            viewModel: GroupStatisticViewModel
        ) {
            binding.apply {
                vm = viewModel
            }
        }

        override val layoutRes: Int
            get() = R.layout.item_report

    }

}