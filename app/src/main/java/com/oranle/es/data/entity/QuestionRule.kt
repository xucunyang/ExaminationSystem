package com.oranle.es.data.entity

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class QuestionRule(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int,
    val examSheetId: Int,
    val size: Int,
    val typeStr: String,
    val fullScore: Double,
    val singleScore: Double
)