package com.oranle.es.data.repository

import androidx.room.Room
import com.oranle.es.app.SessionApp
import com.oranle.es.data.DB

class UserRepository {

    var dataBase: DB? = null

    fun getDB() = dataBase ?: createDB()

    private fun createDB(): DB {
        val result = Room.databaseBuilder(
            SessionApp.instance!!.applicationContext,
            DB::class.java,
            "es.db"
        ).build()

        dataBase = result

        return result
    }
}