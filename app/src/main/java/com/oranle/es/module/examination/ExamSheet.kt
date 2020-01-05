package com.oranle.es.module.examination

import com.oranle.es.data.entity.Assessment
import com.oranle.es.data.entity.ReportRule
import com.oranle.es.data.entity.SingleChoice
import com.oranle.es.data.repository.DBRepository
import timber.log.Timber

data class ExamSheet @JvmOverloads constructor(
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
     *
     */
    val showIntroduction: Boolean = true,
    /**
     *
     */
    val showTip: Boolean = true,
    /**
     * 测试题目
     */
    val singleChoiceList: List<SingleChoice>,
    /**
     * 答案列表
     */
    val answerList: List<String?>?,
    /**
     * 报告规则列表
     */
    val reportRuleList: List<ReportRule>?
) {
    suspend fun saveToDB(): String {
        val msg: String
        val answerStr = answerList?.joinToString(",") ?: ""
        val assessment = Assessment(id, title, introduction, showIntroduction, showTip, answerStr)

        val assessmentDao = DBRepository.getDB().getAssessmentDao()
        val assessmentByTitle = assessmentDao.getAssessmentByTitle(title)

        if (answerList?.size != singleChoiceList.size) {
            return "量表解析错误：题目数量与答案不匹配"
        }

        // TODO 规则解析

        msg = if (assessmentByTitle == null) {
            assessmentDao.addAssessment(assessment)
            DBRepository.getDB().getSingleChoiceDao().addSingleChoices(singleChoiceList)
            Timber.d("assessmentByTitle $assessmentByTitle")
            "量表《${assessment.title}》导入成功"
        } else {
            Timber.d("exist $assessmentByTitle")
            "量表《${assessment.title}》已存在"
        }

        return msg
    }

}