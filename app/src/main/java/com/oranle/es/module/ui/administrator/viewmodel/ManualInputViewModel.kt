package com.oranle.es.module.ui.administrator.viewmodel

import androidx.lifecycle.MutableLiveData
import com.oranle.es.data.entity.User
import com.oranle.es.module.base.BaseViewModel

class ManualInputViewModel : BaseViewModel() {

    val students = MutableLiveData<List<User>>().apply { emptyList<User>() }

    fun loadStudent(user: User) {
        asyncCall(
            {
//                getDB().getUserDao().getUsersByRole()
            },
            {

            }
        )
    }

    fun loadAssessment() {

    }

}