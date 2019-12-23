package com.oranle.es.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "user")
data class User @JvmOverloads constructor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "user_name") val userName: String = "",
    @ColumnInfo(name = "alias") val alias: String = userName,
    /**
     *  @see Role
     */
    @ColumnInfo(name = "role") val role: Int,
    @ColumnInfo(name = "psw") val psw: String
) : Serializable
