package com.oranle.es.module.ui.administrator.viewmodel

import androidx.lifecycle.MutableLiveData
import com.oranle.es.data.entity.Assessment
import com.oranle.es.data.entity.Role
import com.oranle.es.data.entity.User
import com.oranle.es.module.base.BaseViewModel

class ManualInputViewModel : BaseViewModel() {

    val students = MutableLiveData<List<User>>().apply { emptyList<User>() }

    val assessments = MutableLiveData<List<Assessment>>().apply { emptyList<User>() }

    fun loadStudentByManagerId(mamager: User) {
        asyncCall(
            {
                val assessmentList = getDB().getAssessmentDao().getAllAssessments()
                assessments.postValue(assessmentList)
                val studentList = getDB().getUserDao().getUserByManagerId(mamager.id)
                students.postValue(studentList)
            },
            {
                val u1 = User(
                    id = 101,
                    userName = "101",
                    alias = "101",
                    role = Role.Examinee.value,
                    psw = "101",

                    classId = 1,
                    managerId = mamager.id
                )
                val u2 = User(
                    id = 102,
                    userName = "102",
                    alias = "102",
                    role = Role.Examinee.value,
                    psw = "102",

                    classId = 1,
                    managerId = mamager.id
                )

                val listOf = listOf(u1, u2)


                students.value = listOf
            }
        )
    }

    fun loadAssessment() {

    }

}