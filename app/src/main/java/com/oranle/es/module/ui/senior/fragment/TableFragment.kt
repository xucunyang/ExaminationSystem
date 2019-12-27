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
import kotlinx.android.synthetic.main.recyclerview.view.*

class TableFragment : BaseFragment<FragmentTableBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_table

    lateinit var vm : ExamSheetOperateViewModel

    override fun initView() {
        dataBinding?.apply {

            addSheet.setOnClickListener{
                activity?.start<FileImportActivity>()
            }

            vm = getViewModel()

            include.recycler_view.layoutManager = LinearLayoutManager(activity)
            val adapter = Adapter(vm)
            include.recycler_view.adapter = adapter

            vm.items.observe(this@TableFragment, Observer {
                adapter.submitList(it)
            })

            vm.start()

        }
    }

    inner class Adapter(viewModel: ExamSheetOperateViewModel) :
        BaseAdapter<Assessment, ItemExamSheetOperateBinding, ExamSheetOperateViewModel>(viewModel, Diff()) {

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
