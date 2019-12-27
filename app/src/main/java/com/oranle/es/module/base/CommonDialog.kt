package com.oranle.es.module.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Display
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.oranle.es.R
import com.oranle.es.databinding.DialogExamSheetSelectBinding

class CommonDialog(
    val cxt: Context, private val titleContent: String
) : Dialog(cxt, R.style.share_activity_styles) {
    private val defaultDisplay: Display
    private var title: TextView? = null

    lateinit var dataBinding: DialogExamSheetSelectBinding

    init {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        defaultDisplay = windowManager.defaultDisplay
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {

        dataBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.dialog_exam_sheet_select,
                null,
                false
            )

        setContentView(dataBinding.root)

        setCanceledOnTouchOutside(false)

        initWindow()


//        vm = ViewModelProviders.of(cxt as FragmentActivity).
//        dataBinding.rvInclude.recycler_view.adapter = AssessmentListAdapter()
    }

    private fun initWindow() {
        val dialogWindow = this.window
        dialogWindow!!.setGravity(Gravity.CENTER)
        val lp = dialogWindow.attributes
        lp.width = 750
        lp.height = 600
        dialogWindow.attributes = lp
    }


}