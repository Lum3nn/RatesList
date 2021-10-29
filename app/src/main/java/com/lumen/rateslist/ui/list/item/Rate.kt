package com.lumen.rateslist.ui.list.item

data class Rate(
    val name: String,
    val value: Float,
    val date: String
) : RatesListItem