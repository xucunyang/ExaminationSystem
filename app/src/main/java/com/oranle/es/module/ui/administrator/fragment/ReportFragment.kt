package com.oranle.es.module.ui.administrator.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.oranle.es.R
import com.oranle.es.data.entity.Assessment
import com.oranle.es.data.entity.ClassEntity
import com.oranle.es.data.entity.ReportRule
import com.oranle.es.data.entity.User
import com.oranle.es.databinding.FragmentReportBinding
import com.oranle.es.databinding.ItemReportBinding
import com.oranle.es.databinding.ItemReportHorBinding
import com.oranle.es.module.base.BaseAdapter
import com.oranle.es.module.base.BaseFragment
import com.oranle.es.module.examination.viewmodel.TypedScore
import com.oranle.es.module.ui.administrator.viewmodel.GroupStatisticViewModel
import java.io.Serializable

class ReportFragment : BaseFragment<FragmentReportBinding>() {

    companion object {
        fun newInstance(assessment: Assessment? = null, isShowAll: Boolean): ReportFragment {
            val fragment = ReportFragment()
            val args = Bundle()
            assessment?.apply {
                args.putSerializable("assessment", assessment)
            }
            args.putBoolean("isShowAll", isShowAll)
            fragment.arguments = args
            return fragment
        }
    }

    lateinit var mViewModel: GroupStatisticViewModel

    override val layoutId: Int
        get() = R.layout.fragment_report

    override fun initView() {

        val assessment = arguments?.getSerializable("assessment") as? Assessment
        val isShowAll = arguments?.getBoolean("isShowAll", false)

        mViewModel = getViewModel()

        var reportAdapter = ReportAdapter(mViewModel)
        var allReportAdapter = AllSheetAdapter(mViewModel)

        dataBinding?.apply {
            vm = mViewModel

            recyclerView.adapter =
                if (isShowAll == null || !isShowAll) reportAdapter else allReportAdapter
            recyclerView.itemAnimator = DefaultItemAnimator()
            recyclerView.layoutManager = LinearLayoutManager(activity)
            recyclerView.addItemDecoration(
                DividerItemDecoration(
                    activity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        mViewModel.items.observe(this, Observer {
            if (isShowAll == null || isShowAll) {
                allReportAdapter = AllSheetAdapter(mViewModel)
                allReportAdapter.submitList(it)
            } else {
                reportAdapter = ReportAdapter(mViewModel)
                reportAdapter.submitList(it)
            }
        })

        if (isShowAll != null && isShowAll) {
            mViewModel.loadAllReport()
        } else {
            assessment?.apply {
                mViewModel.loadReportByAssessment(assessment)
            }
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

    inner class AllSheetAdapter(vm: GroupStatisticViewModel) :
        BaseAdapter<WrapReportBean, ItemReportHorBinding, GroupStatisticViewModel>(vm) {
        override fun doBindViewHolder(
            binding: ItemReportHorBinding,
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
            get() = R.layout.item_report_hor

    }

}

/**
 *  显示listview用的包装bean
 */
data class WrapReportBean(
    val reportId: Int,
    val index: Int,
    val user: User,
    val clazz: ClassEntity,
    val time: Long,
    val assessment: Assessment,
    val typedScore: List<TypedScore>,
    val rules: List<ReportRule>
) : Serializable{
    fun totalScore(): Float {
        var total = 0F
        typedScore.forEach {
            total += it.score
        }
        return total
    }

}