package com.oranle.es.module.examination

import com.oranle.es.module.examination.single_choice.SingleChoiceBean

data class ExamSheet(
    val id: Int,
    /**
     *  标题
     */
    val title: String,
    /**
     *  量表介绍
     */
    val introduction: String,
    /**
     * 测试题目
     */
    val singleChoiceList: List<SingleChoiceBean>

)