package com.oranle.es.data.dao

import androidx.room.*
import com.oranle.es.data.entity.ReportRule

@Dao
interface ReportRuleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRule(rule: ReportRule)

    @Insert
    suspend fun addRules(rules: List<ReportRule>)

    @Query("select * from report_rule")
    suspend fun getRules(): List<ReportRule>

    @Query("select * from report_rule where sheet_id = :sheetId")
    suspend fun getRulesBySheetId(sheetId: Int): List<ReportRule>

    @Query("select * from report_rule where id = :id")
    suspend fun getRuleById(id: Int): ReportRule

    @Update
    suspend fun updateRule(classEntity: ReportRule): Int

    @Delete
    suspend fun deleteRule(classEntity: ReportRule): Int

}