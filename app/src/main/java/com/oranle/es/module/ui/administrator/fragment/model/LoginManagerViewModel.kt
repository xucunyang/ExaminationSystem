package com.oranle.es.module.ui.administrator.fragment.model

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.viewModelScope
import com.oranle.es.data.entity.Role
import com.oranle.es.data.entity.User

import com.oranle.es.module.base.BaseRecycleViewModel
import com.oranle.es.module.ui.administrator.AddPersonalActivity
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginManagerViewModel(private val activity: Activity) : BaseRecycleViewModel<User>() {

    fun onAddPersonal() {
        activity.startActivity(Intent(activity, AddPersonalActivity::class.java))
    }

    fun load() {
        viewModelScope.launch(UI) {
            val adminList = withContext(IO) {
                getDB().getUserDao().getUsersByRole(Role.Examinee.value)
            }
            notifyItem(adminList)
        }
    }
}
