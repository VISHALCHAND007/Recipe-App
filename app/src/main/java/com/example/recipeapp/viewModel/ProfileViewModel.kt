package com.example.recipeapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.repository.ProfileRepository
import com.example.recipeapp.room.database.RecipeDatabase
import com.example.recipeapp.room.entity.ProfileEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ProfileRepository

    init {
        val dao = RecipeDatabase.getDatabaseInstance(application).profileDao()
        repository = ProfileRepository(dao)
    }

    fun insert(profileEntity: ProfileEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(profileEntity)
    }
    fun delete() = viewModelScope.launch(Dispatchers.IO) {
        repository.delete()
    }
    suspend fun getData(): List<ProfileEntity> {
        return repository.getData()
    }
}