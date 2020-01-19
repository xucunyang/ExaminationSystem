package com.oranle.es.module.ui.administrator.fragment.model

import android.view.View
import androidx.lifecycle.viewModelScope
import com.oranle.es.data.entity.Role
import com.oranle.es.data.entity.User
import com.oranle.es.module.base.BaseRecycleViewModel
import com.oranle.es.module.base.start
import com.oranle.es.module.ui.administrator.AddPersonalActivity
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginManagerViewModel : BaseRecycleViewModel<User>() {

    fun onAddPersonal(v: View) {
        val context = v.context
        context.start<AddPersonalActivity>()
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
