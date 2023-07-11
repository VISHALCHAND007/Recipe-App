package com.example.recipeapp.models

data class MealsModel(
    val idMeal: String,
    val strCategory: String,
    val strInstructions: String,
    val strMeal: String,
    val strMealThumb: String,
    val ingredients: List<Pair<String, String>>
)