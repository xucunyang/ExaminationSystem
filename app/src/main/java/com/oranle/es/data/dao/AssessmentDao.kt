package com.oranle.es.data.dao

import androidx.room.*
import com.oranle.es.data.entity.Assessment

@Dao
interface AssessmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAssessment(assessment: Assessment)

    @Query("select * from assessment where title = :title")
    suspend fun getAssessmentByTitle(title: String): Assessment?

    @Query("select * from assessment")
    suspend fun getAllAssessments(): List<Assessment>

    @Update
    suspend fun updateAssessment(assessment: Assessment): Int

    @Delete
    suspend fun deleteAssessment(assessment: Assessment): Int

}
