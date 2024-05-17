package com.example.coroutinetutorial

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class CoroutineLazy : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine_lazy)


        runBlocking {
            val msgOne =  async(start = CoroutineStart.LAZY)  { getMessageOne() }
            val msgTwo = async(start = CoroutineStart.LAZY) { getMessageTwo() }
            print(msgTwo)
            print(msgOne)
        }
    }

    private suspend fun getMessageOne(): String {
        delay(1000)
        Log.d("Coroutine", "after working in getMessageOne()")
        return "Hello"
    }

    private suspend fun getMessageTwo(): String {
        delay(1000)
        Log.d("Coroutine", "after working in getMessageTwo()")
        return "World"
    }
}