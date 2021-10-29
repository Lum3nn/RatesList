package com.lumen.rateslist

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun dodawanie(){
        //Given
        val a = 5
        val b = 2
        //When
        val sum = a + b
        //Then
        assertEquals(7, sum)
    }
}
