package com.oranle.es.module.ui.senior.fragment

import com.oranle.es.R
import com.oranle.es.databinding.FragmentAdminBinding
import com.oranle.es.module.base.BaseFragment

class AdministratorFragment : BaseFragment<FragmentAdminBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_admin

    override fun initView() {
        dataBinding?.apply {
            addManager.setOnClickListener{
                val addManagerDialog = AddManagerDialog(activity!!)
                addManagerDialog.show(fragmentManager, "")
            }
        }
    }

}
