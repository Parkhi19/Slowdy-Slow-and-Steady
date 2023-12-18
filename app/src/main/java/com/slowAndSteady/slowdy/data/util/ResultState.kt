package com.slowAndSteady.slowdy.data.util

import java.lang.Exception

sealed class ResultState {
    class Success<T>(val data: T) : ResultState()
    class Error(val exception: Throwable) : ResultState()
    object Loading : ResultState()
    object None : ResultState()
}