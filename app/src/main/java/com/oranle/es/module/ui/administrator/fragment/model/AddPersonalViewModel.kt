package com.oranle.es.module.ui.administrator.fragment.model

import android.app.Activity
import android.text.TextUtils
import android.widget.Toast

import androidx.lifecycle.MutableLiveData

import com.oranle.es.app.SessionApp
import com.oranle.es.data.entity.ClassEntity
import com.oranle.es.data.entity.Role
import com.oranle.es.data.entity.User
import com.oranle.es.data.repository.DBRepository
import com.oranle.es.module.base.BaseViewModel
import com.oranle.es.module.base.toast

class AddPersonalViewModel : BaseViewModel() {
    var userLoginName = MutableLiveData<String>()
    var pwd = MutableLiveData<String>()
    var comfirmPwd = MutableLiveData<String>()
    var settingContent = MutableLiveData<String>()
    var name = MutableLiveData<String>()
    var schoolName = MutableLiveData<String>()
    var className = MutableLiveData<String>()
    var classId = MutableLiveData<Int>()
    var sex = MutableLiveData<String>()
    var classes = MutableLiveData<List<ClassEntity>>()

    fun onAddPersonal() {
        if (TextUtils.isEmpty(userLoginName.value)) {
            toast("请输入登录名")
            return
        }
        if (TextUtils.isEmpty(pwd.value)) {
            toast("请输入密码")
            return
        }
        if (TextUtils.isEmpty(comfirmPwd.value)) {
            toast("请确认密码")
            return
        }

        if (TextUtils.isEmpty(name.value)) {
            toast("请输入姓名")
            return
        }

        asyncCall(
            {
                val classIncharge = mutableListOf<String>()
                classes.value?.forEach {
                    classIncharge.add(it.id.toString())
                }
                getDB().getUserDao().addUser(
                    User(
                        userName = userLoginName.value!!,
                        alias = name.value!!,
                        role = Role.Manager.value,
                        psw = pwd.value!!,
                        classId = classId.value!!,
                        className = className.value!!,
                        classIncharge = classIncharge.joinToString(",")
                    )
                )
            },
            {
                toast("已录入")
            }
        )
    }
}
