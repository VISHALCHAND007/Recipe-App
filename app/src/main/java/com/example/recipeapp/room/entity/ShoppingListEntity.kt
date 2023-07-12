package com.example.recipeapp.room.entity

import androidx.annotation.ColorInt
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_list_entity")
data class ShoppingListEntity(
    @ColumnInfo (name = "meal_name")
    val mealName: String,
    @ColumnInfo (name = "ingredients")
    val ingredients: ArrayList<Pair<String, String>>
) {
    @PrimaryKey (autoGenerate = true)
    var id: Int = 0
}