package com.oranle.es.data.repository

import androidx.room.Room
import com.oranle.es.app.SessionApp
import com.oranle.es.data.DB

object DBRepository {

    private var dataBase: DB? = null

    fun getDB() = dataBase ?: createDB()

    private fun createDB(): DB {
        val result = Room.databaseBuilder(
            SessionApp.instance!!,
            DB::class.java,
            "es_data.db"
        ).build()

        dataBase = result

        return result
    }
}