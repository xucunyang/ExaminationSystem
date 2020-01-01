package com.oranle.es.module.ui.senior.fragment

import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.oranle.es.R
import com.oranle.es.data.entity.ClassEntity
import com.oranle.es.data.sp.SpUtil
import com.oranle.es.databinding.FragmentUnitNameBinding
import com.oranle.es.databinding.ItemClassBinding
import com.oranle.es.module.base.BaseAdapter
import com.oranle.es.module.base.BaseFragment
import com.oranle.es.module.ui.senior.SeniorAdminActivity
import com.oranle.es.module.ui.senior.viewmodel.ClassViewModel
import timber.log.Timber

class OrganizationNameFragment : BaseFragment<FragmentUnitNameBinding>() {

    private lateinit var viewmodel: ClassViewModel

    override val layoutId: Int
        get() = R.layout.fragment_unit_name

    override fun initView() {
        viewmodel = getViewModel<ClassViewModel>()

        var classAdapter = ClassAdapter(viewmodel)
        viewmodel.items.observe(this, Observer {

            Timber.d("observe ${it?.size}")
            classAdapter = ClassAdapter(viewmodel)
            classAdapter.notifyDataSetChanged()
        })

        dataBinding?.apply {
            vm = viewmodel
            recyclerView.adapter = classAdapter
            recyclerView.layoutManager = LinearLayoutManager(activity)

            recyclerView.itemAnimator = DefaultItemAnimator()
            recyclerView.layoutManager = LinearLayoutManager(activity)
            recyclerView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        }

        val spUtil = SpUtil.instance
        val organizationName = spUtil.getOrganizationName()

        val nameEt = dataBinding!!.organizationNameEt
        val confirm = dataBinding!!.confirm
        if (!TextUtils.isEmpty(organizationName)) {
            nameEt.setText(organizationName)
            nameEt.isEnabled = false
            confirm.visibility = View.GONE
        } else {
            nameEt.hint = "请设置单位名称，设置后不可更改"
            nameEt.clearFocus()
            confirm.visibility = View.VISIBLE
        }
        confirm.setOnClickListener { v ->
            val name = nameEt.editableText.toString()
            val tip: String
            if (!TextUtils.isEmpty(name)) {
                spUtil.setOrganizationName(name)
                nameEt.isEnabled = false
                confirm.visibility = View.GONE
                tip = "设置成功"
            } else {
                tip = "单位名称为空"
            }
            Toast.makeText(activity, tip, Toast.LENGTH_SHORT).show()
        }

        dataBinding!!.addClass.setOnClickListener { view: View -> (activity as SeniorAdminActivity).showAddClassFrag() }

        viewmodel.start()
    }

    inner class ClassAdapter(viewModel: ClassViewModel) :
        BaseAdapter<ClassEntity, ItemClassBinding, ClassViewModel>(viewModel, Diff()) {
        override fun doBindViewHolder(
            binding: ItemClassBinding,
            item: ClassEntity,
            viewModel: ClassViewModel
        ) {
            binding.clazz = item
            binding.vm = viewModel
        }

        override val layoutRes: Int
            get() = R.layout.item_class

    }

    inner class Diff : DiffUtil.ItemCallback<ClassEntity>() {
        override fun areItemsTheSame(oldItem: ClassEntity, newItem: ClassEntity) =
            oldItem.className.equals(newItem.className)

        override fun areContentsTheSame(oldItem: ClassEntity, newItem: ClassEntity) =
            oldItem.className.equals(newItem.className)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Timber.d("onHiddenChanged $hidden")
        if (!hidden) {
            viewmodel.start()
        }
    }


}
