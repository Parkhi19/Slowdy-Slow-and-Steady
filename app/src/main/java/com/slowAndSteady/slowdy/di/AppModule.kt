package com.slowAndSteady.slowdy.di

import android.content.Context
import androidx.room.Room
import com.slowAndSteady.slowdy.SlowdyApplication.Companion.appContext
import com.slowAndSteady.slowdy.data.DatabaseHelper
import com.slowAndSteady.slowdy.data.dao.HabitDao
import com.slowAndSteady.slowdy.data.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): DatabaseHelper{
        return Room.databaseBuilder(
            context,
            DatabaseHelper::class.java,
            "HabitDatabase"
        ).build()
    }

    @Provides
    @Singleton
    fun provideHabitDao(
        database : DatabaseHelper
    ): HabitDao {
        return database.habitDao()
    }

    @Provides
    @Singleton
    fun provideUserDao(
        database : DatabaseHelper
    ): UserDao {
        return database.userDao()
    }
}