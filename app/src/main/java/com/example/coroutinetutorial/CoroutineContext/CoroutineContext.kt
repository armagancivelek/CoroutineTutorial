package com.example.coroutinetutorial.CoroutineContext

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.coroutinetutorial.CoroutineLazy
import com.example.coroutinetutorial.R
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 *                  Defaults
 *      CoroutineDispatcher -> Dispatchers.Default
 *      Job -> No parent job
 *      CoroutineExceptionHandler -> None
 *      CoroutineName -> "coroutine"
 *
 *
 *      A new coroutine inherits the parent context
 *      Parent context  = Defaults + inherited context + arguments
 *
 *
 *      confined dispatcher // Without parameter -> launch
 *      Inherits coroutineContext from immediate parent coroutine
 *      EVent after delay() or suspending function, it continues to run in the same thread.
 *
 *       dispatcher default // with parameter
 *
 *      Gets its own context
 *      After delay() or suspending function it may change its thread
 *
 *      unconfined dispatcher
 *
 *        After delay() or suspending function it  continues in separate thread
 *
 *
 */

class CoroutineContext : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine_context)

    }


    override fun onResume() {
        super.onResume()
//        runBlocking(Dispatchers.Default) {
//            Log.d("Coroutine", " 1 -> ${coroutineContext} ${Thread.currentThread().name}")
//
//            launch(
//                CoroutineName("First Child") + Dispatchers.Unconfined
//            ) {
//                Log.d("Coroutine", "2 -> ${coroutineContext} ${Thread.currentThread().name}")
//                launch(CoroutineName("child of FirstChild")) {
//                    Log.d("Coroutine", "3 -> ${coroutineContext} ${Thread.currentThread().name}")
//                    delay(3000)
//                    Log.d("Coroutine", "3 after delay -> ${coroutineContext} ${Thread.currentThread().name}")
//                }
//                delay(2000) //
//                Log.d("Coroutine", "2 after delay -> ${coroutineContext} ${Thread.currentThread().name}")
//            }
//
//            launch {
//                Log.d("Coroutine", "4 -> ${coroutineContext} ${Thread.currentThread().name}")
//            }
//        }


        runBlocking(Dispatchers.Default) {

            launch(Dispatchers.Unconfined) {
                Log.d("Coroutine", "4 -> ${coroutineContext} ${Thread.currentThread().name}")
                delay(3000)
                Log.d("Coroutine", "4 -> ${coroutineContext} ${Thread.currentThread().name}")
            }
        }
    }
}