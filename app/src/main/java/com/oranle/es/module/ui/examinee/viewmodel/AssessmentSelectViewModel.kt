package com.oranle.es.module.ui.examinee.viewmodel

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.oranle.es.data.entity.Assessment
import com.oranle.es.data.entity.User
import com.oranle.es.module.base.BaseViewModel
import com.oranle.es.module.base.start
import com.oranle.es.module.base.toast
import com.oranle.es.module.ui.examinee.ExamActivity
import com.oranle.es.module.ui.examinee.ExamineeActivity
import timber.log.Timber

enum class AssessmentType {
    /**
     *  主观测评
     */
    SUBJECTIVE,
    /**
     *  客观测评
     */
    OBJECTIVE
}

class AssessmentSelectViewModel : BaseViewModel() {

    val assessments = MutableLiveData<List<Assessment?>>().apply { emptyList<User>() }

    private var currentAssessment: Assessment? = null

    fun loadAssessments(type: AssessmentType) {
        asyncCall(
            {
                val assessmentList = getDB().getAssessmentDao().getAllAssessments()
                val temp = mutableListOf<Assessment?>()
                temp.add(null)
                val classifyAssessment = classifyAssessment(assessmentList)
                temp.addAll(
                    if (type == AssessmentType.OBJECTIVE)
                        classifyAssessment.first
                    else
                        classifyAssessment.second
                )
                assessments.postValue(temp)
            }
        )
    }

    /**
     *  跳转考试开始页面
     */
    fun toExamineeFragment(v: View) {
        if (currentAssessment == null) {
            toast("请选择量表")
            return
        }
        val activity = v.context as ExamineeActivity
        activity.showExamStartFragment(currentAssessment!!)
    }

    fun onStartExam(v: View, assessment: Assessment?) {
        Timber.d("onStartExam $assessment")

        val activity = v.context as ExamineeActivity

        val extras = Bundle()
        extras.putSerializable("assessment", assessment)
        activity.start<ExamActivity>(extras)
    }

    /**
     *  Note 公用一个viewmodel 切换页面时候需要清理
     */
    fun setCurrentAssessment(assessment: Assessment?) {
        currentAssessment = assessment
    }

    private fun classifyAssessment(assessments: List<Assessment>)
            : Pair<MutableList<Assessment?>, MutableList<Assessment?>> {
        val objList = mutableListOf<Assessment?>()
        val subList = mutableListOf<Assessment?>()
        assessments.forEach {
            if (it.title == "多元智能测评量表") {
                subList.add(it)
            } else {
                objList.add(it)
            }
        }
        return Pair(objList, subList)
    }

}
