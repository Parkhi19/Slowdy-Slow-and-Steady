package com.slowAndSteady.slowdy.data.util

sealed class SlowdyException : Exception() {
    object UserNotFoundException : SlowdyException()
}