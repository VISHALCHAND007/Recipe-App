package com.example.recipeapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.repository.WeeklyMealRepository
import com.example.recipeapp.room.database.IngredientListDatabase
import com.example.recipeapp.room.entity.WeeklyPlanEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeeklyMealViewModel(application: Application): AndroidViewModel(application) {
    val allData: LiveData<List<WeeklyPlanEntity>>
    private val repository: WeeklyMealRepository

    init {
        val dao = IngredientListDatabase.getDatabaseInstance(application)!!.weeklyPlanDao()
        repository = WeeklyMealRepository(dao)
        allData = repository.allData
    }

    fun insert(weeklyPlanEntity: WeeklyPlanEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(weeklyPlanEntity)
    }
}