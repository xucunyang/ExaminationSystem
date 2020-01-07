package com.oranle.es.module.ui.senior.fragment

import com.oranle.es.R
import com.oranle.es.data.entity.Assessment
import com.oranle.es.databinding.FragmentSheetSetBinding
import com.oranle.es.module.base.BaseFragment
import com.oranle.es.module.base.toast
import com.oranle.es.module.ui.senior.SeniorAdminActivity
import com.oranle.es.module.ui.senior.viewmodel.SheetSetViewModel

class SheetSetFragment : BaseFragment<FragmentSheetSetBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_sheet_set

    lateinit var viewModel: SheetSetViewModel

    private var activity: SeniorAdminActivity? = null

    override fun initView() {

        val assessment = arguments?.getSerializable("assessment") as? Assessment

        activity = getActivity() as? SeniorAdminActivity

        if (assessment == null) {
            toast("参数错误")
            return
        }

        viewModel = getViewModel()

        dataBinding?.apply {

            vm = viewModel

            back.setOnClickListener {
                activity?.onTable(null)
            }
        }
        viewModel.load(assessment)
    }

}
