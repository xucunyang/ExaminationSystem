package com.oranle.es.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "class")
data class ClassEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id : Int = 0,
    @ColumnInfo(name = "className") val className : String = "",
    @ColumnInfo(name = "isRegister") val isRegister : Boolean = true,
    @ColumnInfo(name = "setting") val setting : String = ""
)