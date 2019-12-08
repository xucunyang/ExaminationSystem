package com.oranle.es.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.oranle.es.data.dao.UserDao
import com.oranle.es.data.entity.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class DB : RoomDatabase() {

    abstract fun getUserDao(): UserDao

}