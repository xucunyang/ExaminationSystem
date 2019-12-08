package com.oranle.es.module.init

import com.oranle.es.data.entity.Role
import com.oranle.es.data.entity.User
import com.oranle.es.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
        GlobalScope.launch (IO) {
            UserRepository().getDB().getUserDao().addUser(admin)
        }
    }
}