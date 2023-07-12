package com.example.recipeapp.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.recipeapp.room.dao.ShoppingListDao
import com.example.recipeapp.room.dao.WeeklyPlanDao
import com.example.recipeapp.room.entity.ShoppingListEntity
import com.example.recipeapp.room.entity.WeeklyPlanEntity
import com.example.recipeapp.utils.ShoppingListConverter

@Database(
    entities = [ShoppingListEntity::class, WeeklyPlanEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ShoppingListConverter::class)
abstract class IngredientListDatabase : RoomDatabase() {
    abstract fun shoppingListDao(): ShoppingListDao
    abstract fun weeklyPlanDao(): WeeklyPlanDao

    companion object {
        private var INSTANCE: IngredientListDatabase? = null

        fun getDatabaseInstance(mContext: Context): IngredientListDatabase? {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    mContext.applicationContext,
                    IngredientListDatabase::class.java,
                    "ingredients_db"
                ).fallbackToDestructiveMigration()
                    .addTypeConverter(ShoppingListConverter())
                    .build()

                INSTANCE = instance
                INSTANCE
            }
        }
    }
}