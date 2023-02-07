package com.avatar.wan.module_home

import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat
import java.util.Date

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val sdf = SimpleDateFormat("YYMM")
        val date = Date()
        val day = sdf.format(date)
        System.out.println("day = $day")
    }
}