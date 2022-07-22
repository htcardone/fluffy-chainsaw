package com.podium.technicalchallenge

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@OptIn(ExperimentalCoroutinesApi::class)
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    suspend fun fetchData(): String {
        delay(1000)
        return "Hello world"
    }

    @Test
    fun dataShouldBeHelloWorld() = runTest {
        val data = fetchData()
        assertEquals("Hello world", data)
    }
}