package com.example.recipeapp.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weekly_plan_entity")
data class WeeklyPlanEntity(
    @ColumnInfo(name = "meal_name")
    val mealName: String,
    @ColumnInfo(name = "ingredients")
    val ingredients: ArrayList<Pair<String, String>>
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}