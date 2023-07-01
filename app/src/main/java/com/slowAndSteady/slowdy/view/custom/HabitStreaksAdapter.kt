package com.slowAndSteady.slowdy.view.custom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.slowAndSteady.slowdy.R

class HabitStreaksAdapter(private val habitStreaksList: List<Boolean>) :
    RecyclerView.Adapter<HabitStreaksAdapter.StreaksViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StreaksViewHolder {
        val habitView = LayoutInflater.from(parent.context)
            .inflate(R.layout.habit_streaks_view, parent, false)

        return StreaksViewHolder(habitView)
    }

    override fun onBindViewHolder(holder: StreaksViewHolder, position: Int) {
        val status = habitStreaksList[position]

        holder.streaksCard.setBackgroundResource(
            if (status) {
                R.drawable.habit_streaks_done
            } else {
                R.drawable.habit_streaks_skipped
            }
        )
    }

    override fun getItemCount(): Int {
        return habitStreaksList.size
    }

    inner class StreaksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val streaksCard = itemView.findViewById<View>(R.id.habitStreaksCard)
    }
}