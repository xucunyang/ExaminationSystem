package com.oranle.es.module.ui.senior.fragment

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.oranle.es.R
import com.oranle.es.data.entity.User
import com.oranle.es.data.sp.SpUtil
import com.oranle.es.databinding.FragmentAdminBinding
import com.oranle.es.databinding.ItemAdminBinding
import com.oranle.es.module.base.BaseAdapter
import com.oranle.es.module.base.BaseFragment
import com.oranle.es.module.ui.senior.viewmodel.AdminViewModel

class AdministratorFragment : BaseFragment<FragmentAdminBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_admin

    val viewModel by lazy {
        getViewModel<AdminViewModel>()
    }

    private lateinit var adapter: AdminAdapter

    val schoolName by lazy {
        SpUtil.instance.getOrganizationName()
    }

    override fun initView() {
        dataBinding?.apply {
            vm = viewModel

            recyclerView.adapter = AdminAdapter(viewModel)
            recyclerView.layoutManager = LinearLayoutManager(activity)

            addManager.setOnClickListener {
                val addManagerDialog = AddManagerDialog(activity!!)
                addManagerDialog.show(fragmentManager, "")
            }
        }

        viewModel.items.observe(viewLifecycleOwner, Observer {
            adapter = AdminAdapter(viewModel)
            adapter.submitList(it)
        })

        viewModel.load()
    }

    override fun onResume() {
        super.onResume()
        viewModel.load()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            viewModel.load()
        }
    }

    inner class AdminAdapter(viewModel: AdminViewModel) :
        BaseAdapter<User, ItemAdminBinding, AdminViewModel>(viewModel) {
        override fun doBindViewHolder(
            binding: ItemAdminBinding,
            item: User,
            viewModel: AdminViewModel
        ) {
            binding.index = getPosition().toString()
            binding.item = item
            binding.school = schoolName
        }

        override val layoutRes = R.layout.item_admin

    }

}
