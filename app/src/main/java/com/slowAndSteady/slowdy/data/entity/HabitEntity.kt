package com.slowAndSteady.slowdy.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import java.util.Date

@Entity(tableName = "habit_table")
data class HabitEntity(
    @ColumnInfo val habitStartDate : Long,
    @ColumnInfo val habitName : String,
    @ColumnInfo val habitStreaks :  List<Boolean> = emptyList(),
    @ColumnInfo var habitColor : Int,
    @PrimaryKey(autoGenerate = true) val habitID : Int = 0,
) {


     class StreaksConverter {
         @TypeConverter
         fun listToJson(value: List<Boolean>?) = Gson().toJson(value)

         @TypeConverter
         fun jsonToList(value: String) = Gson().fromJson(value, Array<Boolean>::class.java).toList()
     }
 }
