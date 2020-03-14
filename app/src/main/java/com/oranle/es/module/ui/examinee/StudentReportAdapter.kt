package com.oranle.es.module.ui.examinee

import com.oranle.es.R
import com.oranle.es.databinding.ItemExamineeReportBinding
import com.oranle.es.module.ui.administrator.fragment.WrapReportBean
import com.oranle.es.module.ui.administrator.viewmodel.StatisticViewModel

class StudentReportAdapter(
    vm: StatisticViewModel
) : RecyclerWithHeaderAdapter
<WrapReportBean, ItemExamineeReportBinding, StatisticViewModel>(vm) {

    override fun doBindViewHolder(
        binding: ItemExamineeReportBinding,
        item: WrapReportBean,
        viewModel: StatisticViewModel
    ) {
        binding.bean = item
        binding.vm = viewModel
        binding.position = getPosition() - 1
    }

    override val layoutRes: Int
        get() = R.layout.item_examinee_report
}