package com.example.recipeapp.repository

import com.example.recipeapp.room.dao.ProfileDao
import com.example.recipeapp.room.entity.ProfileEntity

class ProfileRepository(private val dao: ProfileDao) {
//    val allData: ProfileEntity = dao.getData()

    suspend fun insert(profileEntity: ProfileEntity) {
        dao.insert(profileEntity)
    }

    suspend fun delete() {
        dao.delete()
    }

    suspend fun getData(): List<ProfileEntity> {
        return dao.getData()
    }
}