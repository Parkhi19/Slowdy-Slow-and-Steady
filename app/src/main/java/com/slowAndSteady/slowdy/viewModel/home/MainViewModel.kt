package com.slowAndSteady.slowdy.viewModel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.slowAndSteady.slowdy.data.entity.HabitEntity
import com.slowAndSteady.slowdy.data.repository.HabitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (private val repository: HabitRepository) : ViewModel() {
    val allHabits = repository.getAllHabits().stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        emptyList()
    )

    fun createAndUpdateHabit(habitEntity: HabitEntity) = viewModelScope.launch {
        repository.createAndUpdateHabit(habitEntity)
    }

    private val _habitColorSelectionIndex = MutableStateFlow(0)
    val habitColorSelectionIndex = _habitColorSelectionIndex.asStateFlow()
    fun markHabit(habitId: Int, habitStatus : Boolean) {
        viewModelScope.launch {

            allHabits.value.firstOrNull {
                it.habitID == habitId
            }?.let {
                val newStreaks = it.habitStreaks + habitStatus
                repository.createAndUpdateHabit(
                    it.copy(
                        habitStreaks = newStreaks
                    )
                )
            }
        }
    }

    fun updateHabitColorIndex(habitIndex: Int){
        _habitColorSelectionIndex.value = habitIndex
    }
}

