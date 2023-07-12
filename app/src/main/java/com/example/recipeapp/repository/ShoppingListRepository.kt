package com.example.recipeapp.repository

import androidx.lifecycle.LiveData
import com.example.recipeapp.room.dao.ShoppingListDao
import com.example.recipeapp.room.entity.ShoppingListEntity

class ShoppingListRepository(private val dao: ShoppingListDao) {
    val alldata: LiveData<List<ShoppingListEntity>> = dao.getAll()

    suspend fun insert(shoppingListEntity: ShoppingListEntity) {
        dao.insert(shoppingListEntity)
    }
    suspend fun delete(mealName: String) {
        dao.delete(mealName)
    }
    suspend fun checkExits(mealName: String): Boolean {
        val listItem = dao.checkExist(mealName)
        return listItem != null
    }
}