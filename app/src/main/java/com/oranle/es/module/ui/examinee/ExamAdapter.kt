package com.oranle.es.module.ui.examinee

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.oranle.es.R
import com.oranle.es.databinding.ItemExamTestBinding
import com.oranle.es.module.examination.FIRST_LETTER
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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VH<ItemExamTestBinding>, position: Int) {
        val binding = holder.binding

        val bean = mData[position]

        binding.apply {
            item = bean.singleChoice
            vm = viewModel

            commit.visibility = if (position + 1 == itemCount) View.VISIBLE else View.GONE
            next.visibility = if (position + 1 == itemCount) View.GONE else View.VISIBLE

            index.text = "第${position + 1}题， 共${itemCount}题"

            next.setOnClickListener {
                if (position + 1 < itemCount && recyclerView != null) {
                    recyclerView!!.smoothScrollToPosition(position + 1)
                }
            }

            choose.clearCheck()

            choose.setOnCheckedChangeListener { radioGroup, _ ->
                val childCount = radioGroup.childCount
                for (index in 0..childCount) {
                    val view = radioGroup.getChildAt(index)
                    if (view is RadioButton && view.isChecked) {
                        bean.selectOption = (index + FIRST_LETTER).toChar().toString()
                    }
                }
            }
        }

        binding.executePendingBindings()
    }

    override fun onPageSelected(itemPosition: Int, itemView: View?) {
    }

    private var recyclerView: RecyclerView? = null

    fun setRecyclerView(rv: RecyclerView) {
        recyclerView = rv
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