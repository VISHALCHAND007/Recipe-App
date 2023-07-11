package com.example.recipeapp.repository

import androidx.lifecycle.LiveData
import com.example.recipeapp.room.FavouriteDao
import com.example.recipeapp.room.FavouriteEntity

class FavouriteRepository(private val dao: FavouriteDao) {
    val allData: LiveData<List<FavouriteEntity>> = dao.getAll()

    suspend fun insert(favouriteEntity: FavouriteEntity) {
        dao.insert(favouriteEntity)
    }
    suspend fun delete(favouriteEntity: FavouriteEntity) {
        dao.delete(favouriteEntity)
    }
    suspend fun checkExist(id: String): Boolean {
        val favourite = dao.checkExist(id)
        return favourite != null
    }
}