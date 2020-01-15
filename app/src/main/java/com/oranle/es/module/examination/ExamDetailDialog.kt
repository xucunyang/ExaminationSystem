package com.oranle.es.module.examination

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.oranle.es.R
import com.oranle.es.data.entity.Assessment
import com.oranle.es.data.entity.User
import com.oranle.es.databinding.DialogExamDetailBinding
import com.oranle.es.databinding.ItemQuestionLayoutBinding
import com.oranle.es.module.base.BaseAdapter
import com.oranle.es.module.base.BaseDialogFragment
import com.oranle.es.module.examination.viewmodel.ExamDetailViewModel
import com.oranle.es.module.examination.viewmodel.ExamShowMode
import com.oranle.es.module.examination.viewmodel.SingleChoiceWrap
import timber.log.Timber

const val FIRST_LETTER = 65

/**
 * 展示测量表的具体内容：介绍，题目
 */
class ExamDetailDialog(
    private val cxt: Context,
    val assessment: Assessment,
    private val examShowMode: ExamShowMode,
    val user: User? = null,
    val reportId: Int = -1
) : BaseDialogFragment<DialogExamDetailBinding>() {

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

        viewModel.load(assessment, examShowMode, user, reportId)

        viewModel.dismissFlag.observe(this, Observer {
            Timber.d("observer dismissFlag $it")
            if (it) {
                dismiss()
            }
        })
    }

    override fun getDialogWidth() = 1280

    override fun getDialogHeight() = 720

    inner class SingleChoiceAdapter(viewModel: ExamDetailViewModel) :
        BaseAdapter<SingleChoiceWrap, ItemQuestionLayoutBinding, ExamDetailViewModel>(viewModel) {

        @SuppressLint("SetTextI18n")
        override fun doBindViewHolder(
            binding: ItemQuestionLayoutBinding,
            bean: SingleChoiceWrap,
            viewModel: ExamDetailViewModel
        ) {

            binding.apply {
                vm = viewModel
                item = bean.singleChoice

                if (examShowMode == ExamShowMode.AnswerShow) {
                    answerLayout.visibility = View.VISIBLE
                    rightAnswer.text = "正确答案：${bean.rightAnswer}"
                    yourAnswer.text = "所选答案：${bean.selectOption}"
                } else {
                    answerLayout.visibility = View.GONE
                }

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
        }

        override val layoutRes: Int
            get() = R.layout.item_question_layout
    }

}