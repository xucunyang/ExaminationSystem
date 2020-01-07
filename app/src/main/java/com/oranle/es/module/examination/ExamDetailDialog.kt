package com.oranle.es.module.examination

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.oranle.es.R
import com.oranle.es.data.entity.Assessment
import com.oranle.es.data.entity.SingleChoice
import com.oranle.es.databinding.DialogExamDetailBinding
import com.oranle.es.databinding.ItemQuestionLayoutBinding
import com.oranle.es.module.base.BaseAdapter
import com.oranle.es.module.base.BaseDialogFragment
import com.oranle.es.module.examination.viewmodel.ExamDetailViewModel
import timber.log.Timber

class ExamDetailDialog(val cxt: Context, val assessment: Assessment) :
    BaseDialogFragment<DialogExamDetailBinding>() {

    override val layoutId: Int
        get() = R.layout.dialog_exam_detail

    lateinit var viewModel: ExamDetailViewModel

    private lateinit var adapter: SingleChoiceAdapter

    private val sheetSelectSet = mutableSetOf<Assessment>()

    private val showSheetReportSet = mutableSetOf<Assessment>()

    private var changeCallBack: ((sheet: Set<Assessment>, report: Set<Assessment>) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ExamDetailViewModel::class.java)
    }

    override fun initView() {

        dataBinding.apply {
            vm = viewModel

            closeBtn.setOnClickListener {

                changeCallBack?.invoke(sheetSelectSet, showSheetReportSet)

                Timber.d(" selectedSheets $sheetSelectSet, $showSheetReportSet")

                dismiss()
            }

            adapter = SingleChoiceAdapter(viewModel)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(cxt)
        }

        viewModel.items.observe(this, Observer {
            Timber.d("observer value ${it.size}")
            adapter = SingleChoiceAdapter(viewModel)
            adapter.submitList(it)
        })

        viewModel.load(assessment)
    }

    override fun getDialogWidth() = 1280

    override fun getDialogHeight() = 720

    inner class SingleChoiceAdapter(viewModel: ExamDetailViewModel) :
        BaseAdapter<SingleChoice, ItemQuestionLayoutBinding, ExamDetailViewModel>(viewModel) {

        override fun doBindViewHolder(
            binding: ItemQuestionLayoutBinding,
            item: SingleChoice,
            viewModel: ExamDetailViewModel
        ) {
            binding.vm = viewModel
            binding.item = item
        }

        override val layoutRes: Int
            get() = R.layout.item_question_layout
    }

}