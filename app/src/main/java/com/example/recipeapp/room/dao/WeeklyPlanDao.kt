package com.example.recipeapp.room.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.recipeapp.room.entity.WeeklyPlanEntity

@Dao
interface WeeklyPlanDao {
    @Insert
    fun insert(weeklyPlanEntity: WeeklyPlanEntity)
}