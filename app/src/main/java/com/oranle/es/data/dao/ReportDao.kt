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

    @Query("select * from sheet_report where id = :id")
    suspend fun getReportById(id: Int): SheetReport

    @Query("select * from sheet_report where userId = :userId")
    suspend fun getReportsByUserId(userId: Int): List<SheetReport>

    @Query("select * from sheet_report where sheetId = :sheetId")
    suspend fun getReportsBySheetId(sheetId: Int): List<SheetReport>

    @Query("select * from sheet_report where userId IN (:userIds)")
    suspend fun getReportsByUserIds(userIds: List<Int>): List<SheetReport>

    @Query("SELECT * FROM sheet_report WHERE userId IN (:userIds) AND sheetId = :sheetId")
    suspend fun getReportsByUserIdAndSheetId(sheetId: Int, userIds: List<Int>): List<SheetReport>

    @Update
    suspend fun updateReport(report: SheetReport): Int

    @Delete
    suspend fun deleteReport(report: SheetReport): Int

    @Query("DELETE FROM sheet_report WHERE id = :reportId")
    suspend fun deleteReportById(reportId: Int)

    @Query("delete from sheet_report where userId = :userId")
    suspend fun deleteReportByUserId(userId: Int)

}