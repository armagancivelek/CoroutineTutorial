package com.example.coroutinetutorial.WithContex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.coroutinetutorial.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/***
 *   with context does not create a new coroutine , it is only shifts the context of the existing coroutine
 *
 *   withContext does not return job
 */

class WithContext : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_with_context)
    }


    override fun onResume() {
        super.onResume()

        GlobalScope.launch {
            Log.d("Coroutine", "Job 1 is running at  ${this.coroutineContext.job}")
            delay(3000)


           withContext(Dispatchers.Main) {
                Log.d("Coroutine", "Job 2 is running at  ${this.coroutineContext.job}")
            }


        }
    }
}