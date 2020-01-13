package com.oranle.es.module.ui.administrator.viewmodel

import androidx.lifecycle.MutableLiveData
import com.oranle.es.data.entity.*
import com.oranle.es.data.sp.SpUtil
import com.oranle.es.module.base.BaseRecycleViewModel
import com.oranle.es.module.ui.administrator.fragment.WrapReportBean
import timber.log.Timber
import java.lang.IllegalArgumentException

class GroupStatisticViewModel : BaseRecycleViewModel<WrapReportBean>() {

    val school = MutableLiveData<List<String>>()

    val classesInCharge = MutableLiveData<List<ClassEntity>>()

    val classNameList = MutableLiveData<List<String>>()

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

                classNameList.postValue(tempClassNameList)
                classesInCharge.postValue(classList)
            },
            {
            }
        )
    }

    fun loadReportByAssessment(assessment: Assessment) {

        asyncCall(
            {
                val currentUser = SpUtil.instance.getCurrentUser()

                if (currentUser == null) {
                    toast("当前登录用户为空，请检查")
                }

                getWrapReportBean(assessment)
            },
            {
                notifyItem(it)
            }
        )

    }


    private suspend fun getWrapReportBean(assessment: Assessment): List<WrapReportBean> {

        val currentUser = SpUtil.instance.getCurrentUser()

        if (currentUser == null) {
            toast("当前登录用户为空，请检查")
            return emptyList()
        }

        val classesInCharge = getAllClassesInCharge(currentUser)

        Timber.d("111111 $classesInCharge")

        val classesIdInCharge = mutableListOf<Int>()
        classesInCharge.forEach {
            classesIdInCharge.add(it.id)
        }

        Timber.d("2222 $classesIdInCharge")

        val allStudents = getAllStudentByClassIds(classesIdInCharge)

        Timber.d("3333 $allStudents")

        val stuIds = mutableListOf<Int>()
        allStudents.forEach {
            stuIds.add(it.id)
        }

        val reports =
            getAllSpecifySheetIdReportByStudentId(assessment.id, stuIds)

        Timber.d("4444 $reports")

        val wrapReportBeans = mutableListOf<WrapReportBean>()

        val rules = getDB().getRuleDao().getRulesBySheetId(assessment.id)

        reports.forEachIndexed { index, it ->
            val student = getStudentById(allStudents, it.userId)
            val classEntity = getClassById(classesInCharge, student.classId)
            wrapReportBeans.add(
                WrapReportBean(
                    index = index,
                    user = student,
                    clazz = classEntity,
                    time = it.testTime,
                    assessment = assessment,
                    rules = rules
                )
            )
        }

        Timber.d("555555 $wrapReportBeans")

        return wrapReportBeans
    }

    private fun getStudentById(students: List<User>, specifyId: Int): User {
        students.forEach {
            if (it.id == specifyId) {
                return it
            }
        }
        throw IllegalArgumentException("can not find user with id: $specifyId")
    }

    private fun getClassById(classes: List<ClassEntity>, specifyId: Int): ClassEntity {
        classes.forEach {
            if (it.id == specifyId) {
                return it
            }
        }
        throw IllegalArgumentException("can not find ClassEntity with id: $specifyId")
    }

    /**
     * 根据班级id查出所有的学生
     */
    suspend fun getAllStudentByClassIds(classIds: List<Int>): List<User> =
        getDB().getUserDao().getUserByClassIdsAndRole(classIds, Role.Examinee.value)

    /**
     *  获取指定集合中的id的，且指定表id的报告
     */
    private suspend fun getAllSpecifySheetIdReportByStudentId(
        sheetId: Int,
        stuIds: List<Int>
    ): List<SheetReport> {
        return getDB().getReportDao().getReportsByUserIdAndSheetId(sheetId, stuIds)
    }


    suspend fun getAllClassesInCharge(manager: User): List<ClassEntity> {
        val classList = mutableListOf<ClassEntity>()
        manager.classInChargeList.forEach {
            if (it.isNotBlank()) {
                val classEntity = getDB().getClassDao().getClassById(it.toInt())
                classList.add(classEntity)
            } else {
                Timber.w("query class by id $it")
            }
        }
        return classList
    }

}