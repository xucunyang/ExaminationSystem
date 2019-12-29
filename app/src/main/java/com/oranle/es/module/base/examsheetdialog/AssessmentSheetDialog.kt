package com.oranle.es.module.base.examsheetdialog

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.oranle.es.R
import com.oranle.es.data.entity.Assessment
import com.oranle.es.data.entity.ClassEntity
import com.oranle.es.databinding.DialogExamSheetSelectBinding
import com.oranle.es.databinding.ItemExamSheetBinding
import com.oranle.es.module.base.BaseAdapter
import timber.log.Timber


class AssessmentSheetDialog(val cxt: Context, val entity: ClassEntity) : DialogFragment() {

    lateinit var dataBinding: DialogExamSheetSelectBinding

    lateinit var vm: ExamSheetViewModel

    private lateinit var adapter: AssessmentListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dataBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.dialog_exam_sheet_select, container, false
        )
        dataBinding.setLifecycleOwner(viewLifecycleOwner)

        initView()

        return dataBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NO_FRAME, R.style.dialogStyle)

        vm = ViewModelProviders.of(this).get(ExamSheetViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        initWindow()
    }

    private fun initWindow() {
        val dialogWindow = dialog.window
        dialogWindow!!.setGravity(Gravity.CENTER)
        val lp = dialogWindow.attributes
        lp.width = 1024
        lp.height = 700
        dialogWindow.attributes = lp
    }

    private fun initView() {

        dataBinding.apply {

            viewmodel = vm

            closeBtn.setOnClickListener {

                vm.saveToDB(entity)

                dismiss()

                Timber.d(" selectedSheets ${entity.sheetList.size}")
            }

            adapter = AssessmentListAdapter(vm, entity)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(cxt)
        }

        vm.items.observe(this, Observer {
            Timber.d("observer value $it")
            adapter = AssessmentListAdapter(vm, entity)
            adapter.submitList(it)
        })

        vm.load()

    }

    inner class AssessmentListAdapter(val vm: ExamSheetViewModel, val entity: ClassEntity) :
        BaseAdapter<Assessment, ItemExamSheetBinding, ExamSheetViewModel>(vm, Diff()) {

        init {
            Timber.d("init $entity")
        }

        override fun doBindViewHolder(
            binding: ItemExamSheetBinding,
            item: Assessment,
            viewModel: ExamSheetViewModel
        ) {
            entity.sheetList.forEach {
                if ((item.id.toString() == it))
                    binding.checkSheet.isChecked = true
            }

            entity.showSheetReportList.forEach {
                if ((item.id.toString() == it))
                    binding.showReportSheet.isChecked = true
            }

            binding.item = item

            binding.checkSheet.setOnCheckedChangeListener { buttonView, isChecked ->
                val sheetList = entity.sheetList
                if (!isChecked) {
                    sheetList.remove(item.id.toString())
                } else {
                    sheetList.add(item.id.toString())
                }
            }

            binding.showReportSheet.setOnCheckedChangeListener { buttonView, isChecked ->
                if (!isChecked) {
                    entity.showSheetReportList.remove(item.id.toString())
                } else {
                    entity.showSheetReportList.add(item.id.toString())
                }
            }
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