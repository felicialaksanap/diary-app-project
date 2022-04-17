package com.example.diaryapp.helper

import java.text.SimpleDateFormat
import java.util.*

object DateHelper {
    fun getCurrentDate() : String {
        val  dateFormat = SimpleDateFormat(
            "dd/MM/yyyy",
            Locale.getDefault()
        )
        val date = Date()
        return dateFormat.format(date)
    }
}