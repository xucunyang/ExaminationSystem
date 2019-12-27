package com.oranle.es.module.base.examsheetdialog

import android.app.Dialog
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
import kotlinx.android.synthetic.main.recyclerview.view.*


class AssessmentSheetDialog(val cxt: Context) : DialogFragment() {

    lateinit var dataBinding: DialogExamSheetSelectBinding

    lateinit var vm: ExamSheetViewModel
    lateinit var assessmentListAdapter: AssessmentListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_exam_sheet_select, container, false)
        initView()
        return dataBinding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
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
        lp.width = 750
        lp.height = 600
        dialogWindow.attributes = lp
    }


    private fun initView() {

        dataBinding.apply {

            closeBtn.setOnClickListener {
                dismiss()
            }

            assessmentListAdapter = AssessmentListAdapter(vm)
            rvInclude.recycler_view.adapter = assessmentListAdapter
            rvInclude.recycler_view.layoutManager = LinearLayoutManager(cxt)
        }

        vm.items.observe(this, Observer {
            assessmentListAdapter.submitList(it)
        })

        vm.load()

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