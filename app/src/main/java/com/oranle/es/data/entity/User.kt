package com.oranle.es.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "user")
data class User @JvmOverloads constructor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "user_name") val userName: String = "",
    @ColumnInfo(name = "alias") val alias: String = userName,
    @ColumnInfo(name = "selected") var selected: Boolean = false,
    /**
     *  @see Role
     */
    @ColumnInfo(name = "role") val role: Int,
    @ColumnInfo(name = "psw") val psw: String,
    /**
     *  姓名
     */
    @ColumnInfo(name = "name") val name: String = userName,
    /**
     *  true man, false female
     */
    @ColumnInfo(name = "sex") val sex: Boolean = true,
    /**
     * birth day
     */
    @ColumnInfo(name = "birth_day") val birthDay: Long = 0L,
    /**
     *  测评用户/管理员 所属班级id
     */
    @ColumnInfo(name = "class_id") val classId: Int = -1,
    /**
     *  测评用户/管理员 所属班级名称
     */
    @ColumnInfo(name = "class_name") val className: String = "",
    /**
     * 所属学校名称
     */
    @ColumnInfo(name = "school_name") val schoolName: String = "",
    /**
     *  管理员所管辖班级, 班级ID用<,>分割
     */
    @ColumnInfo(name = "class_incharge") val classIncharge: String = "",
    /**
     *  管理员角色是否可以登录
     */
    @ColumnInfo(name = "can_login") val canLogin: Boolean = true,
    /**
     *  测评用户关联的管理员id
     */
    @ColumnInfo(name = "manager_id") val managerId: Int = -1
) : Serializable {
    @Ignore
    val classInChargeList = classIncharge.split(",").toList()

    @Ignore
    val sexStr = if (sex) "男" else "女"

    fun getRoleStr() = if (role == Role.Examinee.value) {
        "测评用户"
    } else if (role == Role.Root.value) {
        "高级管理员"
    } else {
        "管理员"
    }
}
