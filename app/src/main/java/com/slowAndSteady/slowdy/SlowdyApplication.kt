package com.slowAndSteady.slowdy

import android.app.Application
import com.slowAndSteady.slowdy.data.DatabaseHelper
import com.slowAndSteady.slowdy.data.repository.HabitRepository

class SlowdyApplication : Application() {
    val database by lazy { DatabaseHelper.getDatabase(this) }
    val habitRepository by lazy { HabitRepository(database.habitDao()) }
}