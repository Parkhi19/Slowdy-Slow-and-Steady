package com.slowAndSteady.slowdy

import android.app.Application
import android.content.Context
import com.slowAndSteady.slowdy.data.DatabaseHelper
import com.slowAndSteady.slowdy.data.repository.HabitRepository
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SlowdyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }
    companion object{
        lateinit var appContext : Context

//        val app get() = appContext as SlowdyApplication
    }
}