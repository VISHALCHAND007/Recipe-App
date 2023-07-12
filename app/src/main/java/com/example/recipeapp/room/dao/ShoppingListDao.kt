package com.example.recipeapp.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.recipeapp.room.entity.ShoppingListEntity

@Dao
interface ShoppingListDao {
    @Insert
    fun insert(shoppingListEntity: ShoppingListEntity)

    @Query ("Delete from shopping_list_entity where meal_name = :mealName")
    fun delete(mealName: String)

    @Query ("Select * from shopping_list_entity where meal_name = :mealName")
    fun checkExist(mealName: String): ShoppingListEntity?

    @Query ("Select * from shopping_list_entity")
    fun getAll(): LiveData<List<ShoppingListEntity>>
}