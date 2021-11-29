package com.lumen.rateslist

import androidx.room.*
import com.lumen.rateslist.ui.list.item.RateItem

@Dao
interface RateResponseDao {

    @Query("SELECT * FROM rate_item WHERE date = :date ORDER BY name ASC ")
    suspend fun fetchRateItems(date : String): List<RateItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(rateItems: List<RateItem>)

    @Query("DELETE FROM rate_item")
    suspend fun deleteAllRateItems()
}
