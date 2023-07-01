package com.slowAndSteady.slowdy.model

import com.slowAndSteady.slowdy.view.custom.HabitStreaks

data class HabitModel(
    val habitStartDate : String,
    val habitName : String,
    val habitStreaks :  List<Boolean>,

)
