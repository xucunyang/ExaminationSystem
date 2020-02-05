package com.oranle.es.module.ui.administrator.fragment.model

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.oranle.es.data.entity.ClassEntity
import com.oranle.es.data.entity.Role
import com.oranle.es.data.entity.User
import com.oranle.es.data.sp.SpUtil
import com.oranle.es.module.base.BaseViewModel
import com.oranle.es.module.base.toast
import timber.log.Timber

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
                        role = Role.Examinee.value,
                        psw = pwd.value!!,
                        classId = classId.value!!,
                        className = className.value!!,
                        schoolName = schoolName.value!!,
                        classIncharge = classIncharge.joinToString(",")
                    )
                )
            },
            {
                toast("已录入")
            }
        )
    }


    fun updateUser(origin: User): Boolean {

        val copy = origin.copy(
            userName = userLoginName.value!!,
            alias = name.value!!,
            role = Role.Examinee.value,
            psw = pwd.value!!,
            classId = classId.value!!,
            className = className.value!!,
            schoolName = schoolName.value!!
        )
        asyncCall({
            getDB().getUserDao().updateUser(copy)
        }, {
            toast("已修改")
        })
        return true
    }

    suspend fun getAllStudentByManager(): List<ClassEntity> {

        val currentUser = SpUtil.instance.getCurrentUser()

        if (currentUser == null) {
            toast("当前登录用户为空，请检查")
            return emptyList()
        }
        return getAllClassesInCharge(currentUser)
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
