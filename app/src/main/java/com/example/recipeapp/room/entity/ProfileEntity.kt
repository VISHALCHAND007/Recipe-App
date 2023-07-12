package com.example.recipeapp.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile_entity")
data class ProfileEntity(
    @ColumnInfo(name = "cuisine_type")
    val cuisineType: String,
    @ColumnInfo(name = "dietary_restrictions")
    val dietaryRestrictions: ArrayList<String>
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}