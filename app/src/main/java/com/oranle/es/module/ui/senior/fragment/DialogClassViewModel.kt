package com.oranle.es.module.ui.senior.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oranle.es.data.entity.ClassEntity
import com.oranle.es.data.entity.Role
import com.oranle.es.data.entity.User
import com.oranle.es.module.base.BaseRecycleViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class DialogClassViewModel : BaseRecycleViewModel<AddOrModifyManagerDialog.ClassSelect>() {

    val userName = MutableLiveData<String>()

    val psw = MutableLiveData<String>()

    val pswConfirm = MutableLiveData<String>()

    val name = MutableLiveData<String>()

    fun load(selectClassList: List<String> = emptyList()) {
        viewModelScope.launch(UI) {
            val list = withContext(IO) {
                getDB().getClassDao().getAllClass()
            }
            Timber.d("load from db getAllClass ${list.size}")

            val mutableListOf = mutableListOf<AddOrModifyManagerDialog.ClassSelect>()

            list.forEachIndexed { index, entity ->
                val contains = selectClassList.contains(entity.className)
                mutableListOf.add(AddOrModifyManagerDialog.ClassSelect(entity.className, contains))
            }

            notifyItem(mutableListOf)
        }
    }

    fun selectAll() {
        items.value?.forEach {
            it.isSelect = true
        }

        items.value?.forEach {
            Timber.d("select all sheet ${it}")
        }

        notifyItem(items.value)
    }

    fun saveToDB(classEntity: ClassEntity?, schoolName: String?): Boolean {

        Timber.d("saveToDB $classEntity, ${items.value}")

        if (userName.value.isNullOrBlank()) {
            toast("请填写用户名")
            return false
        }

        if (psw.value.isNullOrBlank()) {
            toast("请填写密码")
            return false
        }

        if (pswConfirm.value.isNullOrBlank()) {
            toast("请确认密码")
            return false
        }

        if (pswConfirm.value != psw.value) {
            toast("密码不一致")
            return false
        }

        if (name.value.isNullOrBlank()) {
            toast("请填写姓名")
            return false
        }
        viewModelScope.launch(UI) {
            withContext(IO) {

                val classIncharge = mutableListOf<String>()
                items.value?.forEach {
                    if (it.isSelect) {
                        classIncharge.add(it.className)
                    }
                }

                getDB().getUserDao().addUser(
                    User(
                        userName = userName.value!!,
                        alias = name.value!!,
                        role = Role.Manager.value,
                        psw = psw.value!!,
                        classId = classEntity!!.id,
                        className = classEntity.className,
                        classIncharge = classIncharge.joinToString(",")
                    )
                )
            }

            toast("已录入")
        }
        return true
    }

    fun updateUser(origin: User): Boolean {

        val classIncharge = mutableListOf<String>()
        items.value?.forEach {
            if (it.isSelect) {
                classIncharge.add(it.className)
            }
        }

        val copy = origin.copy(
            userName = userName.value!!,
            alias = name.value!!,
            role = Role.Manager.value,
            psw = psw.value!!,
            classIncharge = classIncharge.joinToString(",")
        )
        asyncCall({
            getDB().getUserDao().updateUser(copy)
        }, {
            toast("已修改")
        })
        return true
    }

    fun initOriginData(user: User?) {
        if (user != null) {
            userName.value = user.userName
            psw.value = user.psw
            pswConfirm.value = user.psw
            name.value = user.alias
        } else {
            userName.value = ""
            psw.value = ""
            pswConfirm.value = ""
            name.value = ""
        }
    }

}