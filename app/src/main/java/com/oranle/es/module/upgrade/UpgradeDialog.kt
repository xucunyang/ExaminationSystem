package com.oranle.es.module.upgrade

import android.view.View
import com.oranle.es.R
import com.oranle.es.databinding.DialogUpgradeBinding
import com.oranle.es.module.base.BaseDialogFragment

class UpgradeDialog : BaseDialogFragment<DialogUpgradeBinding>() {

    override val layoutId: Int
        get() = R.layout.dialog_upgrade

    override fun initView() {
        dataBinding.apply {
            cancel.setOnClickListener {
                dismiss()
                listener?.invoke(false)
            }

            content.visibility = if (contentStr.isNotBlank()) View.VISIBLE else View.GONE
            content.text = contentStr
        }
    }

    override fun getDialogHeight() = 300

    override fun getDialogWidth() = 700

    private var listener: ((Boolean) -> Unit)? = null

    private var contentStr: String = ""

    fun setContent(str: String): UpgradeDialog {
        this.contentStr = str
        dataBinding.content.text = contentStr
        dataBinding.content.visibility = if (contentStr.isNotBlank()) View.VISIBLE else View.GONE
        return this
    }

    fun setProgress(progress: Int) {
        dataBinding.progressBar.progress = progress
    }

    fun setOKListener(l: ((Boolean) -> Unit)): UpgradeDialog {
        this.listener = l
        return this
    }

}