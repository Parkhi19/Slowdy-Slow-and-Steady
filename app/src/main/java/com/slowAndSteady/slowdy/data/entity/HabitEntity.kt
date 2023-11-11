package com.slowAndSteady.slowdy.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import java.util.Date
import java.util.UUID

@Entity(tableName = "habit_table")
data class HabitEntity(
    @ColumnInfo val habitStartDate : Long,
    @ColumnInfo val habitName : String,
    @ColumnInfo val habitStreaks :  List<Boolean> = emptyList(),
    @ColumnInfo val habitReminderHour : Int = 0,
    @ColumnInfo val userID : String? = FirebaseAuth.getInstance().currentUser?.uid,
    @ColumnInfo val habitReminderMinute : Int = 0,
    @ColumnInfo var habitColor : Int,
    @PrimaryKey(autoGenerate = false) val habitID : String = UUID.randomUUID().toString(),
) : BaseEntity(habitID) {


     class StreaksConverter {
         @TypeConverter
         fun listToJson(value: List<Boolean>?) = Gson().toJson(value)

         @TypeConverter
         fun jsonToList(value: String) = Gson().fromJson(value, Array<Boolean>::class.java).toList()
     }
 }
