package com.slowAndSteady.slowdy

import android.content.Context
import android.content.SharedPreferences

class SharedPreference {
    private lateinit var sharedPref: SharedPreferences
    private val editor: SharedPreferences.Editor
        get() = sharedPref.edit()

    fun putString(key: String, value: String) {
        editor.putString(key, value).apply()
    }

    fun getString(key: String): String? {
        return sharedPref.getString(key, null)
    }

    fun putInt(key: String, value: Int) {
        editor.putInt(key, value).apply()
    }

    fun getInt(key: String): Int {
        return sharedPref.getInt(key, 0)
    }

    fun putBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value).apply()
    }

    fun getBoolean(key: String): Boolean {
        return sharedPref.getBoolean(key, false)
    }

    var notificationEnabled:Boolean
        get() = getBoolean("notificationEnabled")
        set(value) = putBoolean("notificationEnabled", value)

   var nightModeEnabled:Boolean
        get() = getBoolean("nightModeEnabled")
        set(value) = putBoolean("nightModeEnabled", value)

    companion object {
        lateinit var instance: SharedPreference
        private const val PREFS_NAME = "com.slowAndSteady.slowdy.preferences"
        fun init(context: Context) {
            instance = SharedPreference().apply {
                sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            }

        }
    }


}