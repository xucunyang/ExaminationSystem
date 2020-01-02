package com.oranle.es.module.examination

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.oranle.es.R
import com.oranle.es.data.entity.Assessment
import com.oranle.es.databinding.DialogExamSheetSelectBinding
import com.oranle.es.databinding.ItemExamSheetBinding
import com.oranle.es.module.base.BaseAdapter
import com.oranle.es.module.base.BaseDialogFragment
import com.oranle.es.module.base.examsheetdialog.ExamSheetViewModel
import timber.log.Timber

class ExamDetailDialog(val cxt: Context) : BaseDialogFragment<DialogExamSheetSelectBinding>() {

    override val layoutId: Int
        get() = R.layout.dialog_exam_sheet_select

    lateinit var vm: ExamSheetViewModel

    private lateinit var adapter: AssessmentListAdapter

    private val sheetSelectSet = mutableSetOf<Assessment>()

    private val showSheetReportSet = mutableSetOf<Assessment>()

    private var changeCallBack: ((sheet: Set<Assessment>, report: Set<Assessment>) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm = ViewModelProviders.of(this).get(ExamSheetViewModel::class.java)
    }

    override fun initView() {

        dataBinding.apply {

            viewmodel = vm

            closeBtn.setOnClickListener {

                changeCallBack?.invoke(sheetSelectSet, showSheetReportSet)

                Timber.d(" selectedSheets $sheetSelectSet, $showSheetReportSet")

                dismiss()
            }

            adapter = AssessmentListAdapter(vm)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(cxt)
        }

        vm.items.observe(this, Observer {
            Timber.d("observer value $it")
            adapter = AssessmentListAdapter(vm)
            adapter.submitList(it)
        })

        vm.load()

    }


    inner class AssessmentListAdapter(val vm: ExamSheetViewModel) :
        BaseAdapter<Assessment, ItemExamSheetBinding, ExamSheetViewModel>(vm) {

        init {
            Timber.d("init ")
        }

        override fun doBindViewHolder(
            binding: ItemExamSheetBinding,
            item: Assessment,
            viewModel: ExamSheetViewModel
        ) {
            binding.checkSheet.isChecked = sheetSelectSet.contains(item)
            binding.showReportSheet.isChecked = showSheetReportSet.contains(item)

            binding.item = item

            binding.checkSheet.setOnCheckedChangeListener { buttonView, isChecked ->
                if (!isChecked) {
                    sheetSelectSet.remove(item)
                } else {
                    sheetSelectSet.add(item)
                }
            }

            binding.showReportSheet.setOnCheckedChangeListener { buttonView, isChecked ->
                if (!isChecked) {
                    showSheetReportSet.remove(item)
                } else {
                    showSheetReportSet.add(item)
                }
            }
        }

        override val layoutRes: Int
            get() = R.layout.item_exam_sheet
    }

}