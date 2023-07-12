package com.example.recipeapp.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav_table")
data class FavouriteEntity(
    @PrimaryKey @ColumnInfo(name = "recipe_id")
    val recipeId: String,
    @ColumnInfo(name = "recipe_name")
    val recipeName: String,
    @ColumnInfo(name = "recipe_category")
    val recipeCategory: String,
    @ColumnInfo(name = "recipe_img")
    val recipeImg: String,
    @ColumnInfo(name = "is_favourite")
    val isFavourite: Boolean
)