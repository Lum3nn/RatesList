package com.lumen.rateslist.ui.list.item

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rate_item")
data class RateItem(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "value") val value: Float,
    @ColumnInfo(name = "date") val date: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    ) : RatesListItem
