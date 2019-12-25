package com.oranle.es.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.oranle.es.data.dao.AssessmentDao
import com.oranle.es.data.dao.ClassDao
import com.oranle.es.data.dao.SingleChoiceDao
import com.oranle.es.data.dao.UserDao
import com.oranle.es.data.entity.Assessment
import com.oranle.es.data.entity.ClassEntity
import com.oranle.es.data.entity.SingleChoice
import com.oranle.es.data.entity.User

@Database(entities = [User::class, Assessment::class, SingleChoice::class, ClassEntity::class], version = 1, exportSchema = false)
abstract class DB : RoomDatabase() {

    abstract fun getUserDao(): UserDao

    abstract fun getAssessmentDao(): AssessmentDao

    abstract fun getSingleChoiceDao(): SingleChoiceDao

    abstract fun getClassDao(): ClassDao

}