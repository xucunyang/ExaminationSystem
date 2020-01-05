package com.oranle.es.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.oranle.es.data.dao.*
import com.oranle.es.data.entity.*

@Database(
    entities =
    [
        User::class, Assessment::class, SingleChoice::class, ClassEntity::class, ReportRule::class
    ],
    version = 1, exportSchema = false
)
abstract class DB : RoomDatabase() {

    abstract fun getUserDao(): UserDao

    abstract fun getAssessmentDao(): AssessmentDao

    abstract fun getSingleChoiceDao(): SingleChoiceDao

    abstract fun getClassDao(): ClassDao

    abstract fun getRuleDao(): ReportRuleDao

}