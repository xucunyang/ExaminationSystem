package com.oranle.es.module.ui.senior.viewmodel

import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oranle.es.data.entity.ClassEntity
import com.oranle.es.data.entity.Role
import com.oranle.es.data.entity.User
import com.oranle.es.module.base.BaseRecycleViewModel
import com.oranle.es.module.ui.senior.SeniorAdminActivity
import com.oranle.es.module.ui.senior.fragment.AdministratorFragment
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class AdminViewModel : BaseRecycleViewModel<User>() {

    val eidtLogin = "编辑登陆权限"
    val forbidenLogin = "已禁止登录"
    val operateTip = MutableLiveData<String>(eidtLogin)

    var classes: List<ClassEntity>? = null

    fun load() {
        viewModelScope.launch(UI) {
            val adminList = withContext(IO) {
                classes = getDB().getClassDao().getAllClass()
                getDB().getUserDao().getUsersByRole(Role.Manager.value)
            }
            notifyItem(adminList)
        }
    }

    fun changeSetting(item: User, v: View) {
        if (!item.canLogin) {
            toast("请先开启登录权限")
        } else {
            val activity = v.context as SeniorAdminActivity
            val fragment = activity.getFragment(1) as? AdministratorFragment
            val bundle = Bundle()
            bundle.putSerializable("user", item)
            fragment?.arguments = bundle
            fragment?.showAddOrModifyDialog(true)
        }
    }

    fun onOperate(item: User) {
        viewModelScope.launch(UI) {
            val copy = item.copy(canLogin = !item.canLogin)
            withContext(IO) {
                getDB().getUserDao().updateUser(copy)
            }
            Timber.d("已更新")
            load()
        }
    }

    fun getClassInCharge(ids: String): String {

        val idList = ids.split(",").toList()
        val classInCharge = mutableListOf<String>()

        Timber.d("getClassInCharge ${classes?.size}")

        classes?.forEach {
            if (idList.contains(it.id.toString())) {
                classInCharge.add(it.className)
            }
        }
        return classInCharge.joinToString(",")
    }

}