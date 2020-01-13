package com.oranle.es.data.dao

import androidx.room.*
import com.oranle.es.data.entity.SheetReport

@Dao
interface ReportDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addReport(report: SheetReport)

    @Insert
    suspend fun addReports(reports: List<SheetReport>)

    @Query("select * from sheet_report order by testTime desc")
    suspend fun getAllReport(): List<SheetReport>

    @Query("select * from sheet_report where userId = :userId")
    suspend fun getReportsByUserId(userId: Int): List<SheetReport>

    @Query("select * from sheet_report where sheetId = :sheetId")
    suspend fun getReportsBySheetId(sheetId: Int): List<SheetReport>

    @Update
    suspend fun updateReport(report: SheetReport): Int

    @Delete
    suspend fun deleteRule(report: SheetReport): Int

    @Query("delete from sheet_report where userId = :userId")
    suspend fun deleteReportByUserId(userId: Int)

}