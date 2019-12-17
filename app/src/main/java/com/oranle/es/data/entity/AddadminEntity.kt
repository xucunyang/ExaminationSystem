package com.oranle.es.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "add_admin")
data class AddadminEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id : Int = 0,
    @ColumnInfo(name = "userName") val userName : String = "",
    @ColumnInfo(name = "pwd") val pwd : String = "",
    @ColumnInfo(name = "confirm_pwd") val confirm_pwd : String = "",
    @ColumnInfo(name = "realName") val realName : String = "",
    @ColumnInfo(name = "schoolName") val schoolName : String = "",
    @ColumnInfo(name = "className") val className : String = "",
    @ColumnInfo(name = "isLogin") val isLogin : Boolean = true
)