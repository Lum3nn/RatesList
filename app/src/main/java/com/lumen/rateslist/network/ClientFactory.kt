package com.lumen.rateslist.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ClientFactory {

    const val KEY = "7a0e6ef5bfa55c8c972b7dd727cb720c"
    private const val BASE_URL = "http://data.fixer.io/api/"
//    private const val MOCK_BASE_URL = "http://192.168.1.145:3000/"

    private var apiClient: FixerService? = null

    fun apiClient(): FixerService {
        if (apiClient != null) return apiClient!!

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FixerService::class.java)
            .also { client -> apiClient = client }
    }
}
