package com.oranle.es.module.ui

import android.os.Bundle
import com.oranle.es.R
import com.oranle.es.databinding.ItemQuestionLayoutBinding
import com.oranle.es.module.base.BaseActivity
import kotlinx.android.synthetic.main.item_question_layout.*

class ExaminationSystemLoginActivity : BaseActivity<ItemQuestionLayoutBinding>() {

    override val layoutId: Int
        get() = R.layout.item_question_layout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val s =
            "http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4"
        val ss = "/sdcard/es-web/datiqin.mp3"
        mp3_view.setUp(ss,"饺子闭眼睛")
        mp3_view.thumbImageView.setImageResource(R.drawable.ic_launcher)
    }

}