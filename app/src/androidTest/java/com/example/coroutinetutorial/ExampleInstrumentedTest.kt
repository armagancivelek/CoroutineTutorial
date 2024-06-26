package com.example.coroutinetutorial

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.coroutinetutorial", appContext.packageName)
    }

    @Test fun myFirstTest(){
        Assert.assertEquals(10,5+5)
    }

    @Test
    suspend fun coroutineTest()  = runBlocking{
        delay(100)
        Assert.assertEquals(10,5+5)
    }
}