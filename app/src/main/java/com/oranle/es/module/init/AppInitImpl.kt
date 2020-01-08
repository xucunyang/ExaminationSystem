package com.oranle.es.module.init

import com.oranle.es.data.entity.Role
import com.oranle.es.data.entity.User
import com.oranle.es.data.repository.DBRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

class AppInitImpl: AppInit {
    override fun assessmentDBInit() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun rootUserInit() {
        val admin = User(
            userName = "admin",
            alias = "高级管理员",
            role = Role.Root.value,
            psw = "admin")
        val manager = User(
            userName = "ma",
            alias = "管理员",
            role = Role.Manager.value,
            psw = "1")
        GlobalScope.launch (IO) {
            val usersByRole = DBRepository.getDB().getUserDao().getUsersByRole(Role.Root.value)
            Timber.d("$usersByRole")
            if (usersByRole.isEmpty()) {
                DBRepository.getDB().getUserDao().addUser(admin)
                DBRepository.getDB().getUserDao().addUser(manager)
            }
        }
    }
}