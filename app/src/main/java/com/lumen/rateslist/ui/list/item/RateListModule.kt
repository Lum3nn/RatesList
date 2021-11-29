package com.lumen.rateslist.ui.list.item

import com.lumen.rateslist.RateResponseDao
import com.lumen.rateslist.network.AppDatabase
import com.lumen.rateslist.repository.RateFixerRepository
import com.lumen.rateslist.repository.RateRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RateListFixerModule {

    @Provides
    @Singleton
    fun provideRateFixerRepository(dao : RateResponseDao): RateRepository {
        return RateFixerRepository(dao)
    }
}
