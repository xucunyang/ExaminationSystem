package com.oranle.es.data.repository

import android.content.Context
import androidx.room.Room
import com.oranle.es.data.DB

object DBRepository {

    private var dataBase: DB? = null

    fun getDB(context: Context) = dataBase ?: createDB(context)

    private fun createDB(context: Context): DB {
        val result = Room.databaseBuilder(
            context,
            DB::class.java,
            "es_data.db"
        ).build()

        dataBase = result

        return result
    }
}