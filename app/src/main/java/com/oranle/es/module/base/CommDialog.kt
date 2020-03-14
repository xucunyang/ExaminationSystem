package com.oranle.es.module.base

import android.view.View
import com.oranle.es.R
import com.oranle.es.databinding.DialogCommonBinding

class CommDialog : BaseDialogFragment<DialogCommonBinding>() {

    override val layoutId: Int
        get() = R.layout.dialog_common

    override fun initView() {
        dataBinding.apply {
            ok.setOnClickListener {
                dismiss()
                listener?.invoke(true)
            }
            cancel.setOnClickListener {
                dismiss()
                listener?.invoke(false)
            }
            title.text = titleStr

            content.visibility = if (contentStr.isNotBlank()) View.VISIBLE else View.GONE
            content.text = contentStr
        }
    }

    override fun getDialogHeight() = 400

    override fun getDialogWidth() = 600

    private var listener: ((Boolean) -> Unit)? = null

    private var titleStr: String = "提示"

    private var contentStr: String = ""

    fun setTitle(title: String): CommDialog {
        this.titleStr = title
        return this
    }

    fun setContent(str: String): CommDialog {
        this.contentStr = str
        return this
    }

    fun setOKListener(l: ((Boolean) -> Unit)): CommDialog {
        this.listener = l
        return this
    }

}