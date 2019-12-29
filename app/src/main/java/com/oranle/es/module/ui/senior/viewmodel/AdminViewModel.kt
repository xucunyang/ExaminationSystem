package com.oranle.es.module.ui.senior.viewmodel

import androidx.lifecycle.viewModelScope
import com.oranle.es.data.entity.Role
import com.oranle.es.data.entity.User
import com.oranle.es.module.base.BaseRecycleViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AdminViewModel: BaseRecycleViewModel<User>() {

    fun load() {
        viewModelScope.launch(UI) {
            val adminList = withContext(IO) {
                getDB().getUserDao().getUsersByRole(Role.Manager.value)
            }
            notifyItem(adminList)
        }
    }

    fun changeSetting(item: User) {

    }

}