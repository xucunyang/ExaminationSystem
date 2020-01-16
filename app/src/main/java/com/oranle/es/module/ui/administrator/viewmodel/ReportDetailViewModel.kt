package com.oranle.es.module.ui.administrator.viewmodel

import androidx.lifecycle.MutableLiveData
import com.oranle.es.data.entity.Assessment
import com.oranle.es.data.entity.ClassEntity
import com.oranle.es.data.entity.User
import com.oranle.es.data.sp.SpUtil
import com.oranle.es.module.base.BaseViewModel
import com.oranle.es.module.base.classify
import com.oranle.es.module.ui.administrator.fragment.WrapReportBean
import timber.log.Timber

class ReportDetailViewModel : BaseViewModel() {

    val school = MutableLiveData<List<String>>()

    val classesInCharge = MutableLiveData<List<ClassEntity>>()

    val classNameList = MutableLiveData<List<String>>()

    val assessments = MutableLiveData<List<Assessment>>().apply { emptyList<User>() }

    fun getDetail(bean: WrapReportBean): String {
        val sb = StringBuilder()

        val rules = bean.rules.sortedBy { it.id }
        val scoreList = bean.typedScore.sortedBy { it.ruleId }

        val classifyScore = classify(rules, scoreList)

        sb.append("被测试者在本测验中的${bean.assessment.title}总分为：${bean.totalScore()}")
        sb.append(System.lineSeparator())

        if (rules.size == classifyScore.size) {
            rules.forEachIndexed { index, it ->
                sb.append("${it.typeStr}， 共${it.size}小题，")
                if (it.singleScore != 0F) {
                    sb.append("每题${it.singleScore}分，")
                }
                if (it.wholeScore != 0F) {
                    sb.append("满分${it.wholeScore}分，")
                }
                sb.append("共得分${classifyScore.elementAt(index).score}分")
                sb.append(System.lineSeparator())
            }
        }
        return sb.toString()
    }


    fun load(manager: User) {
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

                classNameList.postValue(tempClassNameList)
                classesInCharge.postValue(classList)
            }
        )
    }


}