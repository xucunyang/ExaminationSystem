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
        val assessment =
            Assessment(
                id = id,
                title = title,
                alias = title,
                introduction = introduction,
                showIntroduction = showIntroduction,
                showTip = showTip,
                correctAnswer = answerStr
            )

        val db = DBRepository.getDB()
        val assessmentDao = db.getAssessmentDao()
        val assessmentByTitle = assessmentDao.getAssessmentByTitle(title)

        val singleChoiceSize = singleChoiceList.size
        if (answerList?.size != singleChoiceSize) {
            return "量表解析错误：题目数量与答案不匹配"
        }

        if (reportRuleList == null) {
            return "量表解析错误：报告规则不存在，请检查"
        } else {
            var total = 0
            for (rule in reportRuleList) {
                total += rule.size
            }
            if (total != singleChoiceSize)
                return "量表解析错误：报告规则与单选数量不一致"
        }

        // 规则解析
        db.getRuleDao().addRules(reportRuleList)

        msg = if (assessmentByTitle == null) {
            assessmentDao.addAssessment(assessment)
            db.getSingleChoiceDao().addSingleChoices(singleChoiceList)
            Timber.d("assessmentByTitle $assessmentByTitle")
            "量表《${assessment.title}》导入成功"
        } else {
            Timber.d("exist $assessmentByTitle")
            "量表《${assessment.title}》已存在"
        }

        return msg
    }

}