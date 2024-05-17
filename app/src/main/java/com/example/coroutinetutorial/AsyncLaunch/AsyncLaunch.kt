package com.example.coroutinetutorial.AsyncLaunch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.coroutinetutorial.R
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.measureTime

/**
 *
 *   Both async and launch are extension functions for Coroutine scopes
 *   both of them creates a new coroutine
 *
 *   launch is known fire and forgot it return a job but it does not return any value
 *   async returns deferred object this is  a subclass of job and when we want to return a value from scope
 *   we need to use this builder.
 *   they also different in terms of exception handler
 *   launch throw the exception as soon as the exception occurs but in deferred until we try to reach the deferred object
 *   any exception does not be throwned
 *
 *   when you want to reach the value which is returning from the scope, you need to use await call
 *
 *
 *
 */

class AsyncLaunch : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_async_launch)
    }

    override fun onResume() {
        super.onResume()


        //    var abc = "asa"

                 // async
//        GlobalScope.launch {
//
//
//            val time = measureTime {
//                var answer1: String? = null
//                var answer2: String? = null
//
//
//                val job1 = async {
//                    Log.d(
//                        "Coroutine",
//                        "NetworkCall 1 is  starting ${Thread.currentThread().name + coroutineContext}"
//                    )
//                    networkCall1()
//                }
//                val job2 = async {
//                    Log.d(
//                        "Coroutine",
//                        "NetworkCall 2 is  starting ${Thread.currentThread().name + coroutineContext}"
//                    )
//                    networkCall2()
//                }
//
//
//
//
//                Log.d(
//                    "Coroutine",
//                    "Answer 1 is ${job1.await()} ${Thread.currentThread().name + coroutineContext}"
//                )
//                Log.d(
//                    "Coroutine",
//                    "Answer 2 is ${job2.await()} ${Thread.currentThread().name + coroutineContext}"
//                )
//
//            }
//            Log.d("Coroutine", "Request took $time")
//
//
//        }



          // launch
        GlobalScope.launch {


            val time = measureTime {
                var answer1: String? = null
                var answer2: String? = null


                val job1 = launch {
                    Log.d(
                        "Coroutine",
                        "NetworkCall 1 is  starting ${Thread.currentThread().name + coroutineContext}"
                    )
                     answer1 = networkCall1()
                }
                val job2 = launch {
                    Log.d(
                        "Coroutine",
                        "NetworkCall 2 is  starting ${Thread.currentThread().name + coroutineContext}"
                    )
                    answer2 = networkCall2()
                }

                job1.join()
                job2.join()

                Log.d(
                    "Coroutine",
                    "Answer 1 is ${answer1} ${Thread.currentThread().name + coroutineContext}"
                )
                Log.d(
                    "Coroutine",
                    "Answer 2 is ${answer2} ${Thread.currentThread().name + coroutineContext}"
                )

            }
            Log.d("Coroutine", "Request took $time")


        }
    }







    suspend fun networkCall1(): String {
        delay(4000)
        return "network call 1"

    }

    suspend fun networkCall2(): String {
        delay(4000)
        return "network call 2"
    }
}