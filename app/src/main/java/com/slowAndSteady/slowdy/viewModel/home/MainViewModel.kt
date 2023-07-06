package com.slowAndSteady.slowdy.viewModel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.slowAndSteady.slowdy.data.entity.HabitEntity
import com.slowAndSteady.slowdy.data.repository.HabitRepository
import kotlinx.coroutines.launch

class MainViewModel (private val repository: HabitRepository) : ViewModel() {
    val allHabits = repository.getAllHabits()

    fun createAndUpdateHabit(habitEntity: HabitEntity) = viewModelScope.launch {
        repository.createAndUpdateHabit(habitEntity)
    }
}

class MainViewModelFactory(private val repository: HabitRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}