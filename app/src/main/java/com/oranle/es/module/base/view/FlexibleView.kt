package com.oranle.es.module.base.view

import android.content.Context
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView

class FlexibleView(
    context: Context,
//    tableHeaders: List<String>,
    data: List<String>
) : LinearLayout(context) {

    init {
//        require(tableHeaders.size == data.size) {
//            "size don't equal, " +
//                    "tableHeaders.size:${tableHeaders.size}," +
//                    "data.size:${data.size}}"
//        }

        orientation = HORIZONTAL
        gravity = Gravity.CENTER

        for (s: String in data) {
            val tv = TextView(context)
            val lp = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            tv.text = s
            addView(tv)
        }
    }

}