package com.oranle.es.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "report_rule")
data class ReportRule(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "sheet_id") val sheetId: Int,
    @ColumnInfo(name = "type_str") val typeStr: String = "",
    @ColumnInfo(name = "size") val size: Int = 0,
    @ColumnInfo(name = "single_score") val singleScore: Float = 0F,
    @ColumnInfo(name = "whole_score") val wholeScore: Float = 0F
)