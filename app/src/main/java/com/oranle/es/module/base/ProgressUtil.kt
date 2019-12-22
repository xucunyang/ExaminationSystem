package com.oranle.es.module.base

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import com.oranle.es.R

class ProgressUtil {

    var mProgressDialog: Dialog? = null

    fun showLoading(context: Context, msg: String? = null) {
        showLoading(context, true, msg)
    }

    fun showLoading(context: Context, isCancelable: Boolean, msg: String? = null) {
        mProgressDialog = Dialog(context, R.style.Progressbar_Circle)

        val view = LayoutInflater.from(context).inflate(R.layout.layout_progress_1, null)

        if (msg != null) {
            val tip = view.findViewById<TextView>(R.id.tip)
            tip.text = msg
        }

        mProgressDialog?.apply {
            setContentView(view)
            setCancelable(isCancelable)
            show()
        }
    }

    fun dismiss() {
        if (mProgressDialog != null) {
            mProgressDialog?.dismiss()
        }
    }
}