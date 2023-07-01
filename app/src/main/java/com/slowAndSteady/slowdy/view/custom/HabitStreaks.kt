package com.slowAndSteady.slowdy.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.slowAndSteady.slowdy.R

class HabitStreaks @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {
    init{
        LayoutInflater.from(context).inflate(R.layout.habit_streaks, this, true)
    }

    fun setHabits(habitStreaksList : List<Boolean>){
        val habitStreaksAdapter = HabitStreaksAdapter(habitStreaksList)
        val horizontalLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        findViewById<RecyclerView>(R.id.habitStreak).apply {
            adapter = habitStreaksAdapter
            layoutManager = horizontalLayoutManager
        }
    }
}