package com.lumen.rateslist.repository

import com.lumen.rateslist.model.RateResponse
import com.lumen.rateslist.network.ClientFactory

class RateFixerRepository : RateRepository {

    private val client = ClientFactory.apiClient()

    override suspend fun listRate(date: String): RateResponse {
        return client.ratesByDate(date)
    }
}
