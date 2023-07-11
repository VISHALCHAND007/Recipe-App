package com.example.recipeapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavouriteEntity::class], version = 1, exportSchema = false)
abstract class FavouriteDatabase: RoomDatabase() {
    abstract fun favouriteDao(): FavouriteDao

    companion object {
        private var INSTANCE: FavouriteDatabase? = null

        fun getDatabaseInstance(mContext: Context): FavouriteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    mContext.applicationContext,
                    FavouriteDatabase::class.java,
                    "fav_database"
                ).fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}