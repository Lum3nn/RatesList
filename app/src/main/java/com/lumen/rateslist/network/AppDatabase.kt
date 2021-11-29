package com.lumen.rateslist.network

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lumen.rateslist.RateResponseDao
import com.lumen.rateslist.ui.list.item.DateItem
import com.lumen.rateslist.ui.list.item.RateItem

@Database(
    entities = [
        RateItem::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters()
abstract class AppDatabase : RoomDatabase() {
    abstract fun rateListDao(): RateResponseDao
}
