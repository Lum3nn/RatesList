package com.lumen.rateslist

import com.lumen.rateslist.repository.RateRepository
import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class CalculateDateTest {

    @Test
    fun isCorrectYesterdayDate(){
        //Given
        val date = "2021-01-01"
        //When
        val calculatedDate = RateRepository.calculateDate(date)
        //Then
        assertEquals("2020-12-31", calculatedDate)
    }

    @Test
    fun isDateFormatCorrect(){
        //Given
        val date = "2021-10-11"
        val expectedYear = "2021"
        val expectedMouth = "10"
        val expectedDay = "10"
        //When
        val calculatedDate = RateRepository.calculateDate(date)
        //Then
        assertEquals("$expectedYear-$expectedMouth-$expectedDay", calculatedDate)
    }

    @Test
    fun isActualDateWhenFirstEnter(){
        //Given
        val firstEnter = ""
        val todaysDate = Date()
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val expectedDate = sdf.format(todaysDate)
        //When
        val calculatedDate = RateRepository.calculateDate(firstEnter)
        //Then
        assertEquals(expectedDate, calculatedDate)
    }

    @Test(expected = ParseException::class)
    fun isCorrectGivenDateFormat(){
        //Given
        val givenDate = "Hello World!"
        //When
        RateRepository.calculateDate(givenDate)
    }
}