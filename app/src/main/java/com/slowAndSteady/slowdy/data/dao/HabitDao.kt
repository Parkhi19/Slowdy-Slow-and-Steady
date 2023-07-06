

package com.slowAndSteady.slowdy.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.slowAndSteady.slowdy.data.entity.HabitEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {

    @Query("SELECT * FROM habit_table")
    fun getHabits(): Flow<List<HabitEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habitName: HabitEntity)

    @Query("DELETE FROM habit_table WHERE habitID = :habitId")
    suspend fun deleteHabitById(habitId: Int)
}
