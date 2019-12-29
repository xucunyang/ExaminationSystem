package com.oranle.es.data.dao

import androidx.room.*
import com.oranle.es.data.entity.Role
import com.oranle.es.data.entity.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: User)

    @Query("select * from user where role = :role")
    suspend fun getUsersByRole(role: Int): List<User>

    @Query("select * from user where user_name = :userName and psw = :psw and role = :role")
    suspend fun getUserByAuth(userName: String, psw: String, role: Int): User

    @Update
    suspend fun updateUser(user: User): Int

    @Delete
    suspend fun deleteUser(user: User): Int

    @Query("delete from user where class_id = :classId and role = :role")
    suspend fun clearExamineeByClassId(classId: Int, role: Int = Role.Examinee.value)

}
