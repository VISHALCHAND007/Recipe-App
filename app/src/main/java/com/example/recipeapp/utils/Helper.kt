package com.example.recipeapp.utils

import android.content.Context
import android.widget.Toast

class Helper {
    companion object {
        var alphabets: ArrayList<String> = ArrayList()
        const val MEAL_RECIPE = "MEAL_RECIPE"
        const val MEAL_NAME = "MEAL_NAME"
        const val MEAL_IMG = "MEAL_IMAGE"
        const val MEAL_INGREDIENTS = "MEAL_INGREDIENTS"
        const val BUNDLE = "BUNDLE"
    }
    fun generateAlphabets() {
        alphabets = ArrayList()
        for (i in 'A'..'Z') {
            alphabets.add(i.toString())

        }
    }
    fun makeToast(mContext: Context, str: String) {
        Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show()
    }
}