package com.example.recipeapp.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.recipeapp.room.entity.ProfileEntity

@Dao
interface ProfileDao {
    @Insert
    fun insert(profileEntity: ProfileEntity)

   @Query ("Delete from profile_entity")
    fun delete()

    @Query ("Select * from profile_entity")
    fun getData(): List<ProfileEntity>
}