package com.oranle.es.module.ui.examinee.viewmodel

import androidx.lifecycle.MutableLiveData
import com.oranle.es.data.entity.Assessment
import com.oranle.es.data.entity.User
import com.oranle.es.module.base.BaseViewModel

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

    fun toExamineeFragment() {

    }

    fun setCurrentAssessment(assessment: Assessment?) {
        currentAssessment = assessment
    }

    private fun classifyAssessment(assessments: List<Assessment>)
            : Pair<MutableList<Assessment?>, MutableList<Assessment?>> {
        val objList = mutableListOf<Assessment?>()
        val subList = mutableListOf<Assessment?>()
        assessments.forEach {
            if (it.title == "多元智能测评量表") {
                objList.add(it)
            } else {
                subList.add(it)
            }
        }
        return Pair(objList, subList)
    }

}
