package com.oranle.es.data.dao

import androidx.room.*
import com.oranle.es.data.entity.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: User)

    @Query("select * from user where role = :role")
    suspend fun getUsersByRole(role: Int): List<User>

    @Update
    suspend fun updateUser(user: User): Int

    @Delete
    suspend fun deleteUser(id: Int): Int

}
