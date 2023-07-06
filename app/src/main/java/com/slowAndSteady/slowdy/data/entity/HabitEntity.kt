package com.slowAndSteady.slowdy.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "habit_table")
 class HabitEntity(
    @ColumnInfo val habitStartDate : Long,
    @ColumnInfo val habitName : String,
    @ColumnInfo val habitStreaks :  List<Boolean> = emptyList(),
    @PrimaryKey(autoGenerate = true) val habitID : Int = 0,
)
