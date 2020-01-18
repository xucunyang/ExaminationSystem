package com.oranle.es.module.ui.administrator.viewmodel

import androidx.lifecycle.MutableLiveData
import com.oranle.es.data.entity.*
import com.oranle.es.data.sp.SpUtil
import com.oranle.es.module.base.BaseViewModel
import com.oranle.es.module.base.toast
import timber.log.Timber

class ManualInputViewModel : BaseViewModel() {

    val students = MutableLiveData<List<User>>().apply { emptyList<User>() }

    val assessments = MutableLiveData<List<Assessment>>().apply { emptyList<User>() }

    fun loadStudentByManagerId(mamager: User) {
        asyncCall(
            {
                val assessmentList = getDB().getAssessmentDao().getAllAssessments()
                assessments.postValue(assessmentList)

                val studentByManager = getAllStudentByManager()

                students.postValue(studentByManager)
            }
        )
    }

    suspend fun getAllStudentByManager(): List<User> {

        val currentUser = SpUtil.instance.getCurrentUser()

        if (currentUser == null) {
            toast("当前登录用户为空，请检查")
            return emptyList()
        }

        val classesInCharge = getAllClassesInCharge(currentUser)
        val classesIdInCharge = mutableListOf<Int>()
        classesInCharge.forEach {
            classesIdInCharge.add(it.id)
        }

        val allStudents = getAllStudentByClassIds(classesIdInCharge)

        val stuIds = mutableListOf<Int>()
        allStudents.forEach {
            stuIds.add(it.id)
        }

        return allStudents
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