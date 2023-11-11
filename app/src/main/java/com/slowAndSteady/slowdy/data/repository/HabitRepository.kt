package com.slowAndSteady.slowdy.data.repository

import com.slowAndSteady.slowdy.SlowdyApplication
import com.slowAndSteady.slowdy.data.dao.HabitDao
import com.slowAndSteady.slowdy.data.entity.HabitEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class HabitRepository @Inject constructor(private val habitDao: HabitDao) : RemoteRepo {

    override val collectionName: String
        get() = "habits"

    fun getAllHabits(): Flow<List<HabitEntity>>{
        return habitDao.getHabits()
    }
    suspend fun createAndUpdateHabit(habitEntity: HabitEntity){
        habitDao.insertHabit(habitEntity)
        SlowdyApplication.appScope.launch(Dispatchers.IO) {
            collectionReference.document(habitEntity.habitID.toString()).set(habitEntity)
        }
    }
    suspend fun deleteHabit(habitId : Int){
        habitDao.deleteHabitById(habitId)
        SlowdyApplication.appScope.launch(Dispatchers.IO) {
            collectionReference.document(habitId.toString()).delete()
        }
    }
    
}