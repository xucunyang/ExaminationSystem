package com.oranle.es.module.ui.administrator.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oranle.es.R
import com.oranle.es.data.entity.Assessment
import com.oranle.es.data.entity.ClassEntity
import com.oranle.es.data.entity.ReportRule
import com.oranle.es.data.entity.User
import com.oranle.es.databinding.FragmentReportBinding
import com.oranle.es.databinding.ItemReportBinding
import com.oranle.es.module.base.BaseAdapter
import com.oranle.es.module.base.BaseFragment
import com.oranle.es.module.examination.viewmodel.TypedScore
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

        var reportAdapter = ReportAdapter(mViewModel)

        dataBinding?.apply {
            vm = mViewModel

            recyclerView.adapter = reportAdapter
            recyclerView.layoutManager =
                LinearLayoutManager(activity) as RecyclerView.LayoutManager?
        }

        mViewModel.items.observe(this, Observer {
            reportAdapter = ReportAdapter(mViewModel)
            reportAdapter.submitList(it)
        })

        assessment?.apply {
            mViewModel.loadReportByAssessment(assessment)
        }
    }

    inner class ReportAdapter(vm: GroupStatisticViewModel) :
        BaseAdapter<WrapReportBean, ItemReportBinding, GroupStatisticViewModel>(vm) {
        override fun doBindViewHolder(
            binding: ItemReportBinding,
            item: WrapReportBean,
            viewModel: GroupStatisticViewModel
        ) {
            binding.apply {
                vm = viewModel
                bean = item
                position = position
            }
        }

        override val layoutRes: Int
            get() = R.layout.item_report

    }

}

/**
 *  显示listview用的包装bean
 */
data class WrapReportBean(
    val index: Int,
    val user: User,
    val clazz: ClassEntity,
    val time: Long,
    val assessment: Assessment,
    val typedScore: List<TypedScore>,
    val rules: List<ReportRule>
)