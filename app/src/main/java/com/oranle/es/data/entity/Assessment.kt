package com.oranle.es.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 *  测评量表
 */
@Entity(tableName = "assessment")
data class Assessment(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    /**
     *  标题
     */
    @ColumnInfo(name = "title") val title: String,
    /**
     *  量表介绍
     */
    @ColumnInfo(name = "intro") val introduction: String = "",
    /**
     * 能否查看此量表说明
     */
    @ColumnInfo(name = "show_intro") val showIntroduction: Boolean,
    /**
     * 能否查看此量表提示 暂不知为何功能 TODO
     */
    @ColumnInfo(name = "show_tip") val showTip: Boolean,
    /**
     *  正确答案  [A, B, C, D]
     */
    @ColumnInfo(name = "correctAnswer") val correctAnswer: String
) {
    @Ignore
    val correctAnswerList = correctAnswer.split(",").toList()
}