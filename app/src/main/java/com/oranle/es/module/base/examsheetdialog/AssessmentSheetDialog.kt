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
import com.oranle.es.databinding.DialogExamSheetSelectBinding
import com.oranle.es.databinding.ItemExamSheetBinding
import com.oranle.es.module.base.BaseAdapter
import timber.log.Timber


class AssessmentSheetDialog(
    val cxt: Context,
    val sheetSelectedSet: Set<Assessment> = setOf(),
    val showSheetReportSelectedSet: Set<Assessment> = setOf()
) : DialogFragment() {

    lateinit var dataBinding: DialogExamSheetSelectBinding

    lateinit var vm: ExamSheetViewModel

    private lateinit var adapter: AssessmentListAdapter

    private val sheetSelectSet = mutableSetOf<Assessment>()

    private val showSheetReportSet = mutableSetOf<Assessment>()

    private var changeCallBack: ((sheet: Set<Assessment>, report: Set<Assessment>) -> Unit)? = null

    init {
        sheetSelectSet.addAll(sheetSelectedSet)
        showSheetReportSet.addAll(showSheetReportSelectedSet)
    }

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

                //                vm.saveToDB(entity)

                if (changeCallBack == null) {
//                    vm.saveToDB(entity)
                } else {
                    changeCallBack?.invoke(sheetSelectSet, showSheetReportSet)
                }

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

    fun setCallBack(callback: ((sheet: Set<Assessment>, report: Set<Assessment>) -> Unit)?) {
        this.changeCallBack = callback
    }

    inner class AssessmentListAdapter(val vm: ExamSheetViewModel) :
        BaseAdapter<Assessment, ItemExamSheetBinding, ExamSheetViewModel>(vm, Diff()) {

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

    inner class Diff : DiffUtil.ItemCallback<Assessment>() {
        override fun areItemsTheSame(oldItem: Assessment, newItem: Assessment) =
            (oldItem.id == newItem.id)

        override fun areContentsTheSame(oldItem: Assessment, newItem: Assessment) =
            (oldItem.id == newItem.id)

    }
}