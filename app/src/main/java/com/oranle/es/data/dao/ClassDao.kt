package com.oranle.es.data.dao

import androidx.room.*
import com.oranle.es.data.entity.ClassEntity

@Dao
interface ClassDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addClass(classEntity: ClassEntity)

    @Query("select * from class")
    suspend fun getAllClass(): List<ClassEntity>

    @Query("select * from class where class_name = :className")
    suspend fun getClassByName(className: String): List<ClassEntity>

    @Query("select * from class where id = :id")
    suspend fun getClassById(id: Int): ClassEntity

    @Update
    suspend fun updateClass(classEntity: ClassEntity): Int

    @Delete
    suspend fun deleteClass(classEntity: ClassEntity): Int

}