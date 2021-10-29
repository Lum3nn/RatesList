package com.lumen.rateslist.repository

import com.lumen.rateslist.model.RateResponse
import java.text.SimpleDateFormat
import java.util.*

interface RateRepository {

    companion object {

        private val calendar = Calendar.getInstance()
        private val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        fun calculateDate(lastDate: String): String {
            return if (lastDate.isEmpty()) {
                val dateNow = Date()
                sdf.format(dateNow)
            } else {
                calendar.time = sdf.parse(lastDate)!!
                calendar.add(Calendar.DATE, -1)
                sdf.format(calendar.time)
            }
        }
    }

    suspend fun listRate(date: String): RateResponse
}