package com.oranle.es.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *  测评量表
 */
@Entity(tableName = "assessment")
data class Assessment (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "desc") val desc: String = "",
    @ColumnInfo(name = "right") val right: Int = 0,
    @ColumnInfo(name = "tips") val tips: String = ""

)