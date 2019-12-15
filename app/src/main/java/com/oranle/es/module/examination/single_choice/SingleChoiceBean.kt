package com.oranle.es.module.examination.single_choice

data class SingleChoiceBean(
    val id: Int = 0,
    /**
     *  所属测评表ID
     */
    val examSheetId: Int,
    /**
     *  题目类型
     */
    val questionType: Int,
    /**
     *  题干
     */
    val question: String,
    /**
     *  题干部分img图片url，可能为多个
     */
    val questionImgUrls: List<String>,
    /**
     *  多媒体url
     */
    val mediaUrl: String,
    /**
     *  子选项
     */
    val options: List<String>,
    /**
     *  正确答案
     */
    val correctAnswer: String
)