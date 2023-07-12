package com.example.recipeapp.utils

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.recipeapp.room.entity.ProfileEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class ListConverter {
    @TypeConverter
    fun fromStringToList(str: String): ArrayList<String> {
        val listType = object: TypeToken<ArrayList<String>>() {}.type
        return Gson().fromJson(str, listType)
    }

    @TypeConverter
    fun fromListToString(list: ArrayList<String>): String {
        return Gson().toJson(list)
    }
}