package com.example.recipeapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.repository.ShoppingListRepository
import com.example.recipeapp.room.database.IngredientListDatabase
import com.example.recipeapp.room.entity.ShoppingListEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShoppingListViewModel(application: Application): AndroidViewModel(application) {
    val allData: LiveData<List<ShoppingListEntity>>
    private val repository: ShoppingListRepository
    init {
        val dao = IngredientListDatabase.getDatabaseInstance(application)!!.shoppingListDao()
        repository = ShoppingListRepository(dao)
        allData = repository.alldata
    }

    fun insert(shoppingListEntity: ShoppingListEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(shoppingListEntity)
    }
    fun delete(mealName: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(mealName)
    }
    suspend fun checkExist(mealName: String): Boolean {
        return repository.checkExits(mealName)
    }
}