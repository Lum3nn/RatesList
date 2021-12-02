package com.lumen.rateslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lumen.rateslist.model.RateResponse
import com.lumen.rateslist.repository.RateRepository
import com.lumen.rateslist.ui.list.RateListViewModel
import com.lumen.rateslist.ui.list.item.DateItem
import com.lumen.rateslist.ui.list.item.RateItem
import com.lumen.rateslist.util.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class RateListViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val todaysDate = Date()
    private val sdf = SimpleDateFormat("yyyy-MM-dd")
    private val actualDate = sdf.format(todaysDate)

    class FakeRatesRepository(val actualDate: String) : RateRepository {

        override suspend fun listRate(date: String): RateResponse {
            return RateResponse(
                "EUR",
                actualDate,
                mapOf(
                    "PLN" to 4.23f,
                    "GBP" to 6.23f,
                )
            )
        }

        override suspend fun deleteAllRates() {
            
        }
    }

    @Test
    fun reloadShouldShowRatesFromActualDay() {
        //Given
        val fakeListRepository = FakeRatesRepository(actualDate)

        val viewModel = RateListViewModel(fakeListRepository)
        viewModel.load() // pobranie drugiej strony z danymi

        //When
        viewModel.reloadData()
        //Then
        //sprawdzamy livedate czy ma poprawna date
        val dateAfterReload = viewModel.rateData.getOrAwaitValue().filterIsInstance<DateItem>()
        assertEquals(1, dateAfterReload.size)
        assertEquals(actualDate, dateAfterReload.first().date)
    }

    @Test
    fun areLoadedActualRates() {
        //Given
        val fakeListRepository = FakeRatesRepository(actualDate)
        //When
        val viewModel = RateListViewModel(fakeListRepository)
        //Then
        val loadedDate = viewModel.rateData.getOrAwaitValue()
        assertEquals(4, loadedDate.size)
    }

    @Test
    fun areLoadedMoreItems() {
        //Given
        val fakeListRepository = FakeRatesRepository(actualDate)
        val viewModel = RateListViewModel(fakeListRepository)
        //When
        viewModel.load() // pobranie drugiej strony z danymi
        //Then
        val rateData = viewModel.rateData.getOrAwaitValue()
        val loadedItemsSize = rateData.size
        val loadedDataSize = rateData.filterIsInstance<DateItem>().size
        val loadedRatesSize = rateData.filterIsInstance<RateItem>().size
        assertEquals(7, loadedItemsSize)
        assertEquals(2, loadedDataSize)
        assertEquals(4, loadedRatesSize)
    }

}