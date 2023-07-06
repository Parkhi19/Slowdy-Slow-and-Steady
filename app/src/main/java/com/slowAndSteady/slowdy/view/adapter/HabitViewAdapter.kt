package com.slowAndSteady.slowdy.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.slowAndSteady.slowdy.databinding.HabitViewBinding
import com.slowAndSteady.slowdy.data.entity.HabitEntity

class HabitViewAdapter(private val habitModelList : List<HabitEntity> ) : RecyclerView.Adapter<HabitViewAdapter.HabitViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val binding = HabitViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HabitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habitModel = habitModelList[position]
        holder.binding.habitName.text = habitModel.habitName
        holder.binding.habitStreakView.setHabits(habitModel.habitStreaks)
    }

    override fun getItemCount(): Int {
            return habitModelList.size
    }

    inner class HabitViewHolder(val binding: HabitViewBinding) :
        RecyclerView.ViewHolder(binding.root)
}