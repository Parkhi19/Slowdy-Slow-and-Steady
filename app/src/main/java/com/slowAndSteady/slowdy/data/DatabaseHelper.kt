package com.slowAndSteady.slowdy.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.slowAndSteady.slowdy.data.dao.HabitDao
import com.slowAndSteady.slowdy.data.dao.UserDao
import com.slowAndSteady.slowdy.data.entity.HabitEntity
import com.slowAndSteady.slowdy.data.entity.UserEntity

@Database(entities = [HabitEntity::class, UserEntity::class],version = 1, exportSchema = false)
@TypeConverters(HabitEntity.StreaksConverter::class)
abstract class DatabaseHelper: RoomDatabase() {
    abstract fun habitDao(): HabitDao
    abstract fun userDao(): UserDao

}