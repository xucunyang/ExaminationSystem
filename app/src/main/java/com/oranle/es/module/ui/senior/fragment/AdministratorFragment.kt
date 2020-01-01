package com.oranle.es.module.ui.senior.fragment

import android.content.DialogInterface
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
import timber.log.Timber

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
                showAddOrModifyDialog()
            }
        }

        viewModel.items.observe(viewLifecycleOwner, Observer {
            Timber.d("observe ${it.size} -- $it")
            adapter = AdminAdapter(viewModel)
            adapter.submitList(it)
        })

        viewModel.load()
    }

    fun showAddOrModifyDialog(isModify: Boolean = false) {
        val addManagerDialog = AddOrModifyManagerDialog(activity!!)
        if (isModify)
            addManagerDialog.arguments = arguments
        addManagerDialog.show(fragmentManager, "")
        addManagerDialog.setDismissListener(DialogInterface.OnDismissListener {
            Timber.d("on dismiss dialog")
            viewModel.load()
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.load()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Timber.d("onHiddenChanged $hidden")
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
            binding.vm = viewModel
        }

        override val layoutRes = R.layout.item_admin

    }

}
