package com.oranle.es.module.examination

import com.oranle.es.app.SessionApp
import com.oranle.es.data.entity.Assessment
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
     *
     */
    val answerList: List<String?>?
) {
    suspend fun saveToDB(): String {
        var msg: String
        val answerStr = answerList?.joinToString(",") ?: ""
        val assessment = Assessment(id, title, introduction, showIntroduction, showTip, answerStr)


        val assessmentDao = DBRepository.getDB(SessionApp.instance!!).getAssessmentDao()
        val assessmentByTitle = assessmentDao.getAssessmentByTitle(title)
        if (assessmentByTitle == null) {
            assessmentDao.addAssessment(assessment)
            DBRepository.getDB(SessionApp.instance!!).getSingleChoiceDao().addSingleChoices(singleChoiceList)
            Timber.d("assessmentByTitle $assessmentByTitle")
            msg = "量表《${assessment.title}》导入成功"
        } else {
            Timber.d("exist $assessmentByTitle")
            msg = "量表《${assessment.title}》已存在"
        }

        return msg
    }

}