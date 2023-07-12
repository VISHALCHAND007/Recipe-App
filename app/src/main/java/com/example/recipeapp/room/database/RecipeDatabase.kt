package com.example.recipeapp.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.recipeapp.room.dao.FavouriteDao
import com.example.recipeapp.room.dao.ProfileDao
import com.example.recipeapp.room.entity.FavouriteEntity
import com.example.recipeapp.room.entity.ProfileEntity
import com.example.recipeapp.utils.ListConverter

@Database(entities = [FavouriteEntity::class, ProfileEntity::class], version = 5, exportSchema = false)

@TypeConverters(ListConverter::class)
abstract class RecipeDatabase: RoomDatabase() {
    abstract fun favouriteDao(): FavouriteDao

    abstract fun profileDao(): ProfileDao

    companion object {
        private var INSTANCE: RecipeDatabase? = null

        fun getDatabaseInstance(mContext: Context): RecipeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    mContext.applicationContext,
                    RecipeDatabase::class.java,
                    "fav_database"
                ).fallbackToDestructiveMigration()
                    .addTypeConverter(ListConverter())
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}