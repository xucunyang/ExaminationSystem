package com.oranle.es.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.oranle.es.module.examination.viewmodel.TypedScore
import com.oranle.es.util.GsonUtil

@Entity(tableName = "sheet_report")
data class SheetReport(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "userId") val userId: Int,
    /**
     *  查询对应的测评表、规则
     */
    @ColumnInfo(name = "sheetId") val sheetId: Int,
    /**
     *  时间
     */
    @ColumnInfo(name = "testTime") val testTime: Long,
    /**
     *  答题详情
     */
    @ColumnInfo(name = "detailString") val detailString: String
) {
    @Ignore
    val getTypedScore = GsonUtil.jsonToList<TypedScore>(
        detailString, TypedScore::class.java
    )
}
