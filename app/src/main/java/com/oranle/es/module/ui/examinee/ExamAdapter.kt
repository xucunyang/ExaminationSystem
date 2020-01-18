package com.oranle.es.module.ui.examinee

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.oranle.es.R
import com.oranle.es.databinding.ItemExamTestBinding
import com.oranle.es.module.examination.viewmodel.ExamDetailViewModel
import com.oranle.es.module.examination.viewmodel.SingleChoiceWrap
import com.oranle.es.module.ui.examinee.widget.VideoPlayAdapter

class ExamAdapter(val viewModel: ExamDetailViewModel) :
    VideoPlayAdapter<ExamAdapter.VH<ItemExamTestBinding>>() {

    private val mData = mutableListOf<SingleChoiceWrap>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            VH<ItemExamTestBinding> {
        val binding = DataBindingUtil.inflate<ItemExamTestBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_exam_test,
            parent,
            false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH<ItemExamTestBinding>, position: Int) {
        val binding = holder.binding

        val bean = mData[position]

        binding.apply {
            item = bean.singleChoice
            vm = viewModel

            commit.visibility = if (position + 1 == itemCount) View.VISIBLE else View.GONE
        }

        binding.executePendingBindings()
    }

    override fun onPageSelected(itemPosition: Int, itemView: View?) {

    }

    override fun getItemCount() = mData.size

    fun setData(data: List<SingleChoiceWrap>) {
        mData.addAll(data)
        notifyDataSetChanged()
    }

    fun clear() {
        mData.clear()
    }

    inner class VH<out Binding : ViewDataBinding>(val binding: Binding) :
        RecyclerView.ViewHolder(binding.root)

}