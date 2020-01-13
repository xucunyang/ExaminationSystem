package com.oranle.es.module.ui.administrator.viewmodel

import androidx.lifecycle.MutableLiveData
import com.oranle.es.data.entity.Assessment
import com.oranle.es.data.entity.ClassEntity
import com.oranle.es.data.entity.SheetReport
import com.oranle.es.data.entity.User
import com.oranle.es.data.sp.SpUtil
import com.oranle.es.module.base.BaseRecycleViewModel
import timber.log.Timber

class GroupStatisticViewModel : BaseRecycleViewModel<SheetReport>() {

    val school = MutableLiveData<List<String>>()

    val classesInCharge = MutableLiveData<List<ClassEntity>>()

    val classeNameList = MutableLiveData<List<String>>()

    val assessments = MutableLiveData<List<Assessment>>().apply { emptyList<User>() }

    fun loadChoiceSuit(manager: User) {
        asyncCall(
            {
                val assessmentList = getDB().getAssessmentDao().getAllAssessments()
                assessments.postValue(assessmentList)

                val schoolName = SpUtil.instance.getOrganizationName()

                schoolName?.apply {
                    school.postValue(listOf(schoolName))
                }

                val classList = mutableListOf<ClassEntity>()
                val tempClassNameList = mutableListOf<String>()
                manager.classInChargeList.forEach {
                    if (it.isNotBlank()) {
                        val classEntity = getDB().getClassDao().getClassById(it.toInt())
                        classList.add(classEntity)
                        tempClassNameList.add(classEntity.className)
                    } else {
                        Timber.w("query class by id $it")
                    }
                }

                classeNameList.postValue(tempClassNameList)
                classesInCharge.postValue(classList)
            },
            {
            }
        )
    }

    fun loadReportByAssessment(assessmentId: Int) {

        asyncCall(
            {
                getDB().getReportDao().getReportsBySheetId(assessmentId)
            },
            {
                notifyItem(it)
            }
        )

    }

}