package com.oranle.es.module.examination.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.oranle.es.data.entity.Assessment
import com.oranle.es.data.entity.SingleChoice
import com.oranle.es.module.base.BaseRecycleViewModel

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

class ExamDetailViewModel : BaseRecycleViewModel<SingleChoice>() {

    val sheetTitle = MutableLiveData<String>()

    val sheetIntroduce = MutableLiveData<String>()

    val showSheetIntro = MutableLiveData<Boolean>()

    fun load(assessment: Assessment) {
        asyncCall({
            getDB().getSingleChoiceDao().getSingleChoicesBySheetId(assessment.id)
        }, {
            val size = it.size
            sheetTitle.value = assessment.title
            sheetIntroduce.value = assessment.introduction
            notifyItem(it)
        })
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