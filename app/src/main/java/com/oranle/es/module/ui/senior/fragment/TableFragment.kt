package com.oranle.es.module.ui.senior.fragment

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.oranle.es.R
import com.oranle.es.data.entity.Assessment
import com.oranle.es.databinding.FragmentTableBinding
import com.oranle.es.databinding.ItemExamSheetOperateBinding
import com.oranle.es.module.base.BaseAdapter
import com.oranle.es.module.base.BaseFragment
import com.oranle.es.module.base.start
import com.oranle.es.module.examination.inportFile.FileImportActivity
import com.oranle.es.module.ui.senior.viewmodel.ExamSheetOperateViewModel
import timber.log.Timber

class TableFragment : BaseFragment<FragmentTableBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_table

    lateinit var vm: ExamSheetOperateViewModel

    override fun initView() {

        vm = getViewModel()

        dataBinding?.apply {

            viewmodel = vm

            addSheet.setOnClickListener {
                activity?.start<FileImportActivity>()
            }


            var adapter = Adapter(vm)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(activity)

            vm.items.observe(this@TableFragment, Observer {
                Timber.d("on Observer $it ")
                adapter = Adapter(vm)
                adapter.notifyDataSetChanged()
            })
        }
    }

    override fun onResume() {
        super.onResume()
        Timber.d("on resume ")
        vm.load()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Timber.d("onHiddenChanged $hidden")
        if (!hidden) {
            vm.load()
        }
    }

    inner class Adapter(viewModel: ExamSheetOperateViewModel) :
        BaseAdapter<Assessment, ItemExamSheetOperateBinding, ExamSheetOperateViewModel>(
            viewModel,
            Diff()
        ) {

        override fun doBindViewHolder(
            binding: ItemExamSheetOperateBinding,
            item: Assessment,
            viewModel: ExamSheetOperateViewModel
        ) {
            binding.vm = viewModel
            binding.item = item
        }

        override val layoutRes: Int
            get() = R.layout.item_exam_sheet_operate

    }

    inner class Diff : DiffUtil.ItemCallback<Assessment>() {
        override fun areItemsTheSame(oldItem: Assessment, newItem: Assessment) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Assessment, newItem: Assessment) =
            oldItem.id == newItem.id
    }
}
