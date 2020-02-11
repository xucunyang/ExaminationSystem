package com.oranle.es.module.examination.viewmodel

import android.content.ContextWrapper
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import com.oranle.es.data.entity.*
import com.oranle.es.module.base.BaseRecycleViewModel
import com.oranle.es.module.base.CommDialog
import com.oranle.es.module.examination.GradeRule
import com.oranle.es.module.ui.examinee.viewmodel.MULTI_SMART_TEST
import com.oranle.es.util.GsonUtil
import timber.log.Timber

enum class ExamShowMode {
    /**
     *  高级管理员浏览模式
     */
    AdminView,
    /**
     *  管理员录入模式
     */
    ManagerInput,
    /**
     *  测评人员测试
     */
    Test,
    /**
     *  查看报告答题情况
     */
    AnswerShow
}

/**
 *  答题对应的单选题模型
 */
data class SingleChoiceWrap(
    val singleChoice: SingleChoice,
    // 正确答案
    val rightAnswer: String,
    // 所选的答案
    var selectOption: String?,
    val rule: ReportRule,
    // 是否为多元智能测评表
    val isMultiSmartSheet: Boolean = false
)

class ExamDetailViewModel : BaseRecycleViewModel<SingleChoiceWrap>() {

    val sheetTitle = MutableLiveData<String>()

    val sheetIntroduce = MutableLiveData<String>()

    val isManualInputMode = MutableLiveData<Boolean>(true)

    val loading = MutableLiveData<Boolean>(false)

    val dismissFlag = MutableLiveData<Boolean>(false)

    var mUser: User? = null
    lateinit var mAssessment: Assessment

    var currentExamShowMode = ExamShowMode.ManagerInput

    private val singleChoiceWraps = mutableListOf<SingleChoiceWrap>()

    fun load(assessment: Assessment, examShowMode: ExamShowMode, user: User?, reportId: Int = -1) {
        isManualInputMode.value = (examShowMode == ExamShowMode.ManagerInput)
        currentExamShowMode = examShowMode
        mUser = user
        mAssessment = assessment

        asyncCall({
            loading.postValue(true)
            val rules = getDB().getRuleDao().getRulesBySheetId(assessment.id)
            val multiTestSheet = getDB().getAssessmentDao().getAssessmentByTitle(MULTI_SMART_TEST)

            Timber.d("get multi smart sheet $multiTestSheet")

            var sheetReport: SheetReport? = null
            if (examShowMode == ExamShowMode.AnswerShow && reportId != -1) {
                sheetReport = getDB().getReportDao().getReportById(reportId)
            }

            val singleChoiceList =
                getDB().getSingleChoiceDao().getSingleChoicesBySheetId(assessment.id)
            singleChoiceList.forEachIndexed { index, origin ->
                singleChoiceWraps.add(
                    SingleChoiceWrap(
                        singleChoice = origin,
                        rightAnswer = assessment.correctAnswerList[index],
                        selectOption = if (sheetReport != null) sheetReport.getTypedScore[index].select else null,
                        rule = getRuleByIndex(rules, index),
                        isMultiSmartSheet = multiTestSheet?.id == assessment.id
                    )
                )
            }
            sheetTitle.postValue(assessment.title)
            sheetIntroduce.postValue(assessment.introduction)

            singleChoiceWraps
        }, {
            loading.value = false
            notifyItem(it)
        })
    }

    fun submitAnswer(v: View) {

        val undoList = mutableListOf<Int>()

        val answerDetail = mutableListOf<TypedScore>()

        items.value?.forEachIndexed { index, it ->
            if (it.selectOption == null) {
                undoList.add(index + 1)
            } else {
                answerDetail.add(
                    TypedScore(
                        index = index,
                        ruleId = it.rule.id,
                        select = it.selectOption!!,
                        score = GradeRule.gradeScore(it)
                    )
                )
            }
        }

        if (undoList.isNotEmpty()) {
            CommDialog()
                .setTitle("提示")
                .setContent("请全部回答完毕后，再提交答案！${System.lineSeparator()} 未回答的题号：$undoList")
                .setOKListener {
                    Timber.d("setOKListener $it")
                }.show(getContext(v)?.supportFragmentManager, "")
        } else {
            if (mUser == null) {
                toast("用户信息为空，请检查")
                dismissDialog()
                return
            }

            val gsonString = GsonUtil.GsonString(answerDetail)

            asyncCall(
                {
                    getDB().getReportDao().addReport(
                        SheetReport(
                            userId = mUser!!.id,
                            sheetId = mAssessment.id,
                            testTime = System.currentTimeMillis(),
                            detailString = gsonString
                        )
                    )

                    val reportsByUserId = getDB().getReportDao().getReportsByUserId(mUser!!.id)
                    reportsByUserId
                },
                {
                    Timber.d("submit reportsByUserId $it")
                    dismissDialog()
                }
            )
        }

        Timber.d("submitAnswer $undoList")
    }

    fun checkIfNeedTip(call: (Boolean) -> Unit, v: View) {

        val undoList = mutableListOf<Int>()

        items.value?.forEachIndexed { index, it ->
            if (it.selectOption == null) {
                undoList.add(index + 1)
            }
        }

        val msg = if (undoList.isNotEmpty()) {
            "尚未回答完毕，确认离开吗？${System.lineSeparator()} 未回答的题号：$undoList"
        } else {
            "尚未提交答案，确认离开吗？"
        }
        CommDialog()
            .setTitle("提示")
            .setContent(msg)
            .setOKListener(call)
            .show(getContext(v)?.supportFragmentManager, "")

    }

    private fun dismissDialog() {
        dismissFlag.value = true
    }

    private fun getContext(v: View): FragmentActivity? {
        var context = v.context
        while (context is ContextWrapper) {
            if (context is FragmentActivity) {
                return context
            }
            context = context.baseContext
        }
        return null
    }

    private fun getRuleByIndex(rules: List<ReportRule>, index: Int): ReportRule {
        var currentRange = 0
        for (rule in rules) {
            currentRange += rule.size
            if (index + 1 <= currentRange) {
                return rule
            }
        }
        throw IllegalArgumentException("can not find rule match single choice index: $index")
    }

    fun isHorizontal(singleChoice: SingleChoice): Boolean {
        val imgUrlsList = singleChoice.questionImgUrlsList()
        val mediaExist = singleChoice.mediaUrl.isNotBlank()
        return imgUrlsList.isNotEmpty() && imgUrlsList[0].isNotEmpty() && !mediaExist
    }

    fun firstPicVisibility(singleChoice: SingleChoice): Int {
        val imgUrlsList = singleChoice.questionImgUrlsList()
        return if (imgUrlsList.isNotEmpty() && imgUrlsList[0].isNotEmpty()) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    fun getFirstImage(singleChoice: SingleChoice): String? {
        val imgUrlsList = singleChoice.questionImgUrlsList()

        return if (imgUrlsList.isNotEmpty() && imgUrlsList[0].isNotEmpty())
            imgUrlsList[0]
        else
            null
    }

    fun getSecondImage(singleChoice: SingleChoice): String? {
        val imgUrlsList = singleChoice.questionImgUrlsList()

        return if (imgUrlsList.size > 1 && imgUrlsList[1].isNotEmpty())
            imgUrlsList[1]
        else
            null
    }

    fun secondPicVisibility(singleChoice: SingleChoice): Int {
        val imgUrlsList = singleChoice.questionImgUrlsList()
        return if (imgUrlsList.size > 1 && imgUrlsList[1].isNotEmpty()) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    fun mediaPlayVisibility(singleChoice: SingleChoice): Int {
        return if (singleChoice.mediaUrl.isNotEmpty() && currentExamShowMode == ExamShowMode.Test ) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    fun mediaPlayInit(singleChoice: SingleChoice): Int {
        return if (singleChoice.mediaUrl.isNotEmpty()) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

}

data class TypedScore(
    val index: Int,
    val ruleId: Int,
    val select: String,
    val score: Float
)