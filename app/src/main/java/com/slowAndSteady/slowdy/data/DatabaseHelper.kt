package com.slowAndSteady.slowdy.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.slowAndSteady.slowdy.data.dao.HabitDao
import com.slowAndSteady.slowdy.data.entity.HabitEntity

@Database(entities = [HabitEntity::class],version = 1, exportSchema = false)
abstract class DatabaseHelper: RoomDatabase() {
    abstract fun habitDao(): HabitDao
    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: DatabaseHelper? = null

        fun getDatabase(context: Context): DatabaseHelper {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseHelper::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}