package com.example.recipeapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.repository.FavouriteRepository
import com.example.recipeapp.room.database.RecipeDatabase
import com.example.recipeapp.room.entity.FavouriteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteViewModel(application: Application) : AndroidViewModel(application) {
    val allData: LiveData<List<FavouriteEntity>>
    private val repository: FavouriteRepository

    init {
        val dao = RecipeDatabase.getDatabaseInstance(application).favouriteDao()
        repository = FavouriteRepository(dao)
        allData = repository.allData
    }

    fun insert(favouriteEntity: FavouriteEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(favouriteEntity)
    }
    fun delete(favouriteEntity: FavouriteEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(favouriteEntity)
    }
    suspend fun checkExist(id: String): Boolean {
        return repository.checkExist(id)
    }
}