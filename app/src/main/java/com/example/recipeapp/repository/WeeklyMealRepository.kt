package com.example.recipeapp.repository

import com.example.recipeapp.room.dao.WeeklyPlanDao
import com.example.recipeapp.room.entity.WeeklyPlanEntity

class WeeklyMealRepository(private val dao: WeeklyPlanDao) {
    suspend fun insert(weeklyPlanEntity: WeeklyPlanEntity) {
        dao.insert(weeklyPlanEntity)
    }
}