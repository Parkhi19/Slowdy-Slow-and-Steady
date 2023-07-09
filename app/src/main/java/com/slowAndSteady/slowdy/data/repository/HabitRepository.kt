package com.slowAndSteady.slowdy.data.repository

import com.slowAndSteady.slowdy.data.dao.HabitDao
import com.slowAndSteady.slowdy.data.entity.HabitEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HabitRepository @Inject constructor(private val habitDao: HabitDao) {

    fun getAllHabits(): Flow<List<HabitEntity>>{
        return habitDao.getHabits()
    }
    suspend fun createAndUpdateHabit(habitEntity: HabitEntity){
        habitDao.insertHabit(habitEntity)
    }
    suspend fun deleteHabit(habitId : Int){
        habitDao.deleteHabitById(habitId)
    }
    
}