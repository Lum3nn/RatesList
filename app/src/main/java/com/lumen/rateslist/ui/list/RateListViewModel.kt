package com.lumen.rateslist.ui.list

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumen.rateslist.Event
import com.lumen.rateslist.model.RateResponse
import com.lumen.rateslist.repository.RateFixerRepository
import com.lumen.rateslist.repository.RateRepository
import com.lumen.rateslist.ui.list.item.Date
import com.lumen.rateslist.ui.list.item.Loading
import com.lumen.rateslist.ui.list.item.Rate
import com.lumen.rateslist.ui.list.item.RatesListItem
import kotlinx.coroutines.launch
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class RateListViewModel : ViewModel() {

    private val rateRepository: RateRepository = RateFixerRepository()
    private var lastFetchDate: String = ""
    private var isDataLoading = false

    private var _rateData: MutableLiveData<List<RatesListItem>> = MutableLiveData()
    val rateData: LiveData<List<RatesListItem>> get() = _rateData

    private var _isRefreshing: MutableLiveData<Boolean> = MutableLiveData(false)
    val isRefreshing: LiveData<Boolean> get() = _isRefreshing

    private var _errorMessage: MutableLiveData<Event<String>> = MutableLiveData()
    val errorMessage: LiveData<Event<String>> get() = _errorMessage

    init {
        load()
    }

    fun load() {
        if (isDataLoading) return

        isDataLoading = true
        viewModelScope.launch {
            val currentItems: MutableList<RatesListItem> =
                _rateData.value?.toMutableList() ?: mutableListOf()
            lastFetchDate = calculateDate(lastFetchDate)

            try {
                val data = rateRepository.listRate(lastFetchDate)

                currentItems -= Loading
                currentItems += createListItems(data)
                currentItems += Loading

                _rateData.value = currentItems
                isDataLoading = false
                _isRefreshing.value = false
            } catch (e: Exception) {
                isDataLoading = false
                val errorMessage = e.message ?: ""
                _errorMessage.value = Event(errorMessage)
            }
        }
    }

    fun reloadData() {
        _rateData.value = emptyList()
        lastFetchDate = ""
        load()
    }

    @SuppressLint("SimpleDateFormat")
    private fun calculateDate(lastDate: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        return if (lastDate.isEmpty()) {
            val dateNow = Calendar.getInstance().time
            sdf.format(dateNow)
        } else {
            val c = Calendar.getInstance()
            c.time = sdf.parse(lastDate)
            c.add(Calendar.DATE, -1)
            sdf.format(c.time)
        }
    }

    private fun createListItems(response: RateResponse): List<RatesListItem> {
        val list = mutableListOf<RatesListItem>()
        val responseDate = response.date
        val date = Date(responseDate)
        list.add(date)

        val rates: List<Rate> = response.rates.map { Rate(it.key, it.value, responseDate) }
        list.addAll(rates)

        return list
    }
}
