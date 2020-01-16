package com.oranle.es.module.ui.administrator.dialog

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.oranle.es.R
import com.oranle.es.data.sp.SpUtil
import com.oranle.es.databinding.DialogReporrtDetailBinding
import com.oranle.es.module.base.BaseDialogFragment
import com.oranle.es.module.ui.administrator.fragment.WrapReportBean
import com.oranle.es.module.ui.administrator.viewmodel.ReportDetailViewModel

class ReportDetailDialog : BaseDialogFragment<DialogReporrtDetailBinding>() {

    companion object {
        fun showDialog(activity: FragmentActivity, bean: WrapReportBean) {
            val reportDetailDialog = ReportDetailDialog()
            val data = Bundle()
            data.putSerializable("wrap_bean", bean)
            reportDetailDialog.arguments = data
            reportDetailDialog.show(activity.supportFragmentManager, "")
        }
    }

    override val layoutId: Int
        get() = R.layout.dialog_reporrt_detail

    override fun initView() {
        val wrapBean = arguments?.getSerializable("wrap_bean") as? WrapReportBean

        wrapBean?.let { bean ->
            dataBinding.apply {
                school = SpUtil.instance.getOrganizationName()
                item = bean
                vm = ReportDetailViewModel()

                closeBtn.setOnClickListener {
                    dismiss()
                }
            }
        }
    }

}