package com.slowAndSteady.slowdy.utils

import java.util.Calendar
import java.util.Date

object Utils {

    private val months = arrayOf(
        "JAN", "FEB", "MAR", "APR",
        "MAY", "JUN", "JUL", "AUG",
        "SEP", "OCT", "NOV", "DEC"
    )

    fun getFormattedDate(date: Calendar): String {
        val day = date[Calendar.DAY_OF_MONTH]
        val month = date[Calendar.MONTH]
        val year = date[Calendar.YEAR]
        return "${months[month]} ${day.toString().padStart(2, '0')}, $year"
    }
}