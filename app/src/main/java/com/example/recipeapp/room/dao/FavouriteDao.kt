package com.example.recipeapp.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.recipeapp.room.entity.FavouriteEntity

@Dao
interface FavouriteDao {
    @Insert
    fun insert(favouriteEntity: FavouriteEntity)

    @Delete
    fun delete(favouriteEntity: FavouriteEntity)

    @Query ("Select * from fav_table")
    fun getAll(): LiveData<List<FavouriteEntity>>

    @Query ("Select * from fav_table where recipe_id = :recipeId")
    fun checkExist(recipeId: String): FavouriteEntity?
}