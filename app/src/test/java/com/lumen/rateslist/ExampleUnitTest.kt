package com.lumen.rateslist

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
//class ExampleUnitTest {
//    @Test
//    fun addition_isCorrect() {
//        assertEquals(4, 2 + 2)
//    }
//}

fun main() {
    println("Hello!")

    val items = mutableListOf("A", "B", "C")
//    items.add("D")
//    items.add("E")
//    items.add("F")
    items.addAll(listOf("D", "E", "F"))

    println(items)


}