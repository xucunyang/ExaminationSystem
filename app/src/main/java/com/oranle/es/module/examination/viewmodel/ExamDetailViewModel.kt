package com.oranle.es.module.examination.viewmodel

import android.content.ContextWrapper
import android.view.ContextThemeWrapper
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import com.oranle.es.data.entity.Assessment
import com.oranle.es.data.entity.ReportRule
import com.oranle.es.data.entity.SingleChoice
import com.oranle.es.module.base.BaseRecycleViewModel
import com.oranle.es.module.base.CommDialog
import timber.log.Timber
import java.lang.IllegalArgumentException

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
    Test
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
    val rule: ReportRule
)

class ExamDetailViewModel : BaseRecycleViewModel<SingleChoiceWrap>() {

    val sheetTitle = MutableLiveData<String>()

    val sheetIntroduce = MutableLiveData<String>()

    val isManualInputMode = MutableLiveData<Boolean>(false)

    val loading = MutableLiveData<Boolean>(false)

    val singleChoiceWraps = mutableListOf<SingleChoiceWrap>()

    fun load(assessment: Assessment, manualModel: Boolean = false) {
        isManualInputMode.value = manualModel

        asyncCall({
            loading.postValue(true)
            val rules = getDB().getRuleDao().getRulesBySheetId(assessment.id)

            val singleChoiceList =
                getDB().getSingleChoiceDao().getSingleChoicesBySheetId(assessment.id)
            singleChoiceList.forEachIndexed { index, origin ->
                singleChoiceWraps.add(
                    SingleChoiceWrap(
                        singleChoice = origin,
                        rightAnswer = assessment.correctAnswerList[index],
                        selectOption = null,
                        rule = getRuleByIndex(rules, index)
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

        items.value?.forEachIndexed { index, it ->

            if (it.selectOption == null) {
                undoList.add(index + 1)
            }
        }

        CommDialog()
            .setTitle("提示哦")
            .setContent("this is not complete $undoList")
            .setOKListener {
                Timber.d("setOKListener $it")
            }.show(getContext(v)?.supportFragmentManager, "")

        Timber.d("submitAnswer $undoList")

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

    fun isVertical(singleChoice: SingleChoice): Boolean {
        val imgUrlsList = singleChoice.questionImgUrlsList()
        return imgUrlsList.isNotEmpty() && imgUrlsList[0].isNotEmpty()
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
        return if (singleChoice.mediaUrl.isNotEmpty()) {
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