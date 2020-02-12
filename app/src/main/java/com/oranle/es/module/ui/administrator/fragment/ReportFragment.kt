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
import com.oranle.es.module.ui.administrator.viewmodel.StatisticViewModel
import java.io.Serializable

class ReportFragment : BaseFragment<FragmentReportBinding>() {

    companion object {
        fun newInstance(
            assessment: Assessment? = null,
            clazz: ClassEntity? = null,
            isShowAll: Boolean = false,
            studentId: Int = -1
        ): ReportFragment {
            val fragment = ReportFragment()
            val args = Bundle()
            assessment?.apply {
                args.putSerializable("assessment", assessment)
            }
            args.putSerializable("clazz", clazz)
            // 所有班级所有表
            args.putBoolean("isShowAll", isShowAll)
            args.putInt("studentId", studentId)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var mViewModel: StatisticViewModel

    override val layoutId: Int
        get() = R.layout.fragment_report

    override fun initView() {

        val assessment = arguments?.getSerializable("assessment") as? Assessment
        val isShowAll = arguments?.getBoolean("isShowAll", false)
        val clazz = arguments?.getSerializable("clazz") as? ClassEntity
        val studentId = arguments?.getInt("studentId", -1)

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
        } else if (studentId != null && studentId != -1 && assessment != null) {
            mViewModel.loadReportByStudentUserIdAndSheetId(studentId, assessment.id)
        } else {
            mViewModel.loadReport(assessment, clazz)
        }
    }

    inner class ReportAdapter(vm: StatisticViewModel) :
        BaseAdapter<WrapReportBean, ItemReportBinding, StatisticViewModel>(vm) {
        override fun doBindViewHolder(
            binding: ItemReportBinding,
            item: WrapReportBean,
            viewModel: StatisticViewModel
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

    inner class AllSheetAdapter(vm: StatisticViewModel) :
        BaseAdapter<WrapReportBean, ItemReportHorBinding, StatisticViewModel>(vm) {
        override fun doBindViewHolder(
            binding: ItemReportHorBinding,
            item: WrapReportBean,
            viewModel: StatisticViewModel
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
 *  显示list view用的包装bean
 */
data class WrapReportBean(
    val reportId: Int,
    val index: Int,
    val user: User,
    val clazz: ClassEntity,
    val time: Long,
    val assessment: Assessment,
    val typedScore: List<TypedScore>,
    val rules: List<ReportRule>,
    val isMultiSmartSheet: Boolean
) : Serializable {
    fun totalScore(): Float {
        var total = 0F
        typedScore.forEach {
            total += it.score
        }
        return total
    }

}