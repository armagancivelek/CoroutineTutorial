package com.example.coroutinetutorial.RunBlocking

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.coroutinetutorial.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 *   ! RunBlocking scope will block the thread which is running inside
 *   ! Do not use this scope inside another scope
 *   ! This scope for test
 *   ! It is a bridge to main thread and coroutine world
 *
 */

class RunBlocking : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_run_blocking)


    }

    override fun onResume() {
        super.onResume()

        runBlocking {
            Log.d("Coroutine",this.coroutineContext.toString() + "Thread: ${Thread.currentThread().name}")
            delay(2500)
        }



        Log.d("Coroutine","Main process is finished" + "Thread: ${Thread.currentThread().name}")
    }
}