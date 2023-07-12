package com.example.recipeapp.utils

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class ShoppingListConverter {
    @TypeConverter
    fun fromStringToList(str: String): ArrayList<Pair<String, String>> {
        val listType = object : TypeToken<ArrayList<Pair<String, String>>>() {}.type
        return Gson().fromJson(str, listType)
    }

    @TypeConverter
    fun fromListToString(list: ArrayList<Pair<String, String>>): String {
        return Gson().toJson(list)
    }
}