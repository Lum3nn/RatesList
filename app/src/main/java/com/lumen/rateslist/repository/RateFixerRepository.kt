package com.lumen.rateslist.repository

import com.lumen.rateslist.RateResponseDao
import com.lumen.rateslist.model.RateResponse
import com.lumen.rateslist.network.ClientFactory
import com.lumen.rateslist.ui.list.item.RateItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RateFixerRepository @Inject constructor(private val rateResponseDao: RateResponseDao) :
    RateRepository {

    private val client = ClientFactory.apiClient()


    override suspend fun listRate(date: String): RateResponse {

        val ratesFromDb = rateResponseDao.fetchRateItems(date)
        if (ratesFromDb.isEmpty()) {
            val rateResponseFromServer = client.ratesByDate(date)

            val mapedRateResponse = rateResponseFromServer.rates.map {
                RateItem(it.key, it.value, rateResponseFromServer.date)
            }

            rateResponseDao.insertAll(mapedRateResponse)

            return rateResponseFromServer
        } else {
            val rateResponseFromDao = rateResponseDao.fetchRateItems(date)
            val mapedRateList = rateResponseFromDao.map { it.name to it.value }.toMap()
            return RateResponse(base = "EUR", date, mapedRateList)
        }
    }

    override suspend fun deleteAllRates() {
            rateResponseDao.deleteAllRateItems()
    }
}
