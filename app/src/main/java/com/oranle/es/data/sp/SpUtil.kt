package com.oranle.es.data.sp

import android.content.Context
import android.content.SharedPreferences
import com.oranle.es.app.SessionApp

class SpUtil private constructor() {

    companion object {
        val instance: SpUtil by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            SpUtil()
        }
    }

    private val FIRST_OPEN = "FIRST_OPEN"

    private val editor: SharedPreferences.Editor
    private val sp = SessionApp.instance!!.getSharedPreferences("sp", Context.MODE_PRIVATE)

    init {
        editor = sp.edit()
    }

    private inline fun wrap(block: () -> Unit) {
        block()
        editor.commit()
    }

    fun isFirstOpen(): Boolean {
        val isFirst = sp.getBoolean(FIRST_OPEN, true)
        if (isFirst) {
            wrap {
                editor.putBoolean(FIRST_OPEN, false)
            }
        }
        return isFirst
    }

}