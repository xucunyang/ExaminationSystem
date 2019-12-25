package com.oranle.es.module.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import com.oranle.es.R
import com.oranle.es.data.entity.Assessment
import com.oranle.es.data.repository.DBRepository
import com.oranle.es.databinding.DialogExamSheetSelectBinding
import com.oranle.es.databinding.ItemExamSheetBinding
import kotlinx.android.synthetic.main.dialog_exam_sheet_select.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AssessmentSheetDialog(context: Context) : DialogFragment() {

    lateinit var dataBinding: DialogExamSheetSelectBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // TODO CREATE view model bind

        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_exam_sheet_select, container, false)
        initView()
        return dataBinding.root
    }


    private fun initView() {

        close_btn.setOnClickListener {
            dismiss()
        }



        GlobalScope.launch(UI) {
            val list = withContext(IO) {
                DBRepository.getDB().getAssessmentDao().getAllAssessments()
            }

            // TODO bind recyclerview

        }
    }

    inner class ExamSheetViewModel : BaseRecycleViewModel<Assessment>() {

        fun load() {
            viewModelScope.launch(UI) {
                val list = withContext(IO) {
                    getDB().getAssessmentDao().getAllAssessments()
                }

            }
        }

        var selectedSheets = mutableListOf<Assessment>()
        var selectedShowReportSheet = mutableListOf<Assessment>()

        fun onSheetSelect(assessment: Assessment) {
            selectedSheets.add(assessment)
        }


        fun onShowReportSelect(assessment: Assessment) {
            selectedShowReportSheet.add(assessment)
        }

    }

    inner class AssessmentListAdapter(vm: ExamSheetViewModel) :
        BaseAdapter<Assessment, ItemExamSheetBinding, ExamSheetViewModel>(vm, Diff()) {
        override fun doBindViewHolder(
            binding: ItemExamSheetBinding,
            item: Assessment,
            viewModel: ExamSheetViewModel
        ) {
            binding.item = item
        }

        override val layoutRes: Int
            get() = R.layout.item_exam_sheet
    }

    inner class Diff : DiffUtil.ItemCallback<Assessment>() {
        override fun areItemsTheSame(oldItem: Assessment, newItem: Assessment) =
            (oldItem.id == newItem.id)

        override fun areContentsTheSame(oldItem: Assessment, newItem: Assessment) =
            (oldItem.id == newItem.id)

    }
}