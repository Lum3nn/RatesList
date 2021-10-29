package com.lumen.rateslist.repository

import com.lumen.rateslist.model.RateResponse

interface RateRepository {
    suspend fun listRate(date : String): RateResponse
}