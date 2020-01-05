package com.oranle.es.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "single_choice")
data class SingleChoice(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    /**
     *  所属测评表ID
     */
    @ColumnInfo(name = "examSheetId") val examSheetId: Int,
    /**
     *  题目类型
     */
    @ColumnInfo(name = "questionType") val questionType: Int,
    /**
     *  题干
     */
    @ColumnInfo(name = "question") val question: String,
    /**
     *  题干部分img图片url，可能为多个
     */
    @ColumnInfo(name = "questionImgUrls") val questionImgUrls: String,
    /**
     *  多媒体url
     */
    @ColumnInfo(name = "mediaUrl") val mediaUrl: String,
    /**
     *  子选项
     */
    @ColumnInfo(name = "options") val options: String
) {
    fun questionImgUrlsList(): List<String> =
        questionImgUrls.split(",")

    fun optionList(): List<String> =
        options.split("  ")
}