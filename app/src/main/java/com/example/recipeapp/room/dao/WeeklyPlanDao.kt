package com.example.recipeapp.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.recipeapp.room.entity.WeeklyPlanEntity

@Dao
interface WeeklyPlanDao {
    @Insert
    fun insert(weeklyPlanEntity: WeeklyPlanEntity)
    @Query ("Select * from weekly_plan_entity")
    fun getAllData(): LiveData<List<WeeklyPlanEntity>>


}