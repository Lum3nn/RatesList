package com.lumen.rateslist.ui.list.item

import com.lumen.rateslist.repository.RateFixerRepository
import com.lumen.rateslist.repository.RateRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RateListModule {

    @Provides
    @Singleton
    fun provideRateFixer(): RateRepository {
        return RateFixerRepository()
    }
}