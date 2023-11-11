package com.slowAndSteady.slowdy

import android.app.Application
import android.content.Context
import com.slowAndSteady.slowdy.data.DatabaseHelper
import com.slowAndSteady.slowdy.data.repository.HabitRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.SupervisorJob

@HiltAndroidApp
class SlowdyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this

        SharedPreference.init(this)

    }
    companion object{
        lateinit var appContext : Context

        val appScope = MainScope()

    }
}