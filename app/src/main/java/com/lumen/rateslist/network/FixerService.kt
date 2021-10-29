package com.lumen.rateslist.network

import com.lumen.rateslist.model.RateResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface FixerService {
    @GET("{date}?access_key=${ClientFactory.KEY}")
    suspend fun ratesByDate(@Path("date", encoded = true) date: String): RateResponse
}