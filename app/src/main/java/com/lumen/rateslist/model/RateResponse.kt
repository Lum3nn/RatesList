package com.lumen.rateslist.model

data class RateResponse(
    val base : String,
    val date : String,
    val rates : Map<String, Float>
)
