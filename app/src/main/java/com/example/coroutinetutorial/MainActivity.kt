package com.example.coroutinetutorial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.coroutinetutorial.AsyncLaunch.AsyncLaunch
import com.example.coroutinetutorial.CoroutienScope.CoroutineScope
import com.example.coroutinetutorial.CoroutineContext.CoroutineContext
import com.example.coroutinetutorial.CoroutineWithRetrofit.CoroutineWithRetrofit
import com.example.coroutinetutorial.JobsAndCancellation.JobAndCancellation
import com.example.coroutinetutorial.RunBlocking.RunBlocking
import com.example.coroutinetutorial.WithContex.WithContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield
import kotlin.time.measureTime

class MainActivity : AppCompatActivity() {
    private lateinit var mCoroutineScope : Button
    private lateinit var mCoroutineJobsAndCancellation : Button
    private lateinit var mCorouitneRunBlocking : Button
    private lateinit var mCorouitneWithContext : Button
    private lateinit var mCorouitneAsyncLaunch : Button
    private lateinit var mCorouitneWithRetrofit: Button
    private lateinit var mCorouitneLazy: Button
    private lateinit var mCorouitneContext: Button
    val TAG = "CoroutineTutorial"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        eventHandler();
    }

    private fun init(){
        mCoroutineScope = findViewById(R.id.btn_coroutineScope);
        mCoroutineJobsAndCancellation = findViewById(R.id.btn_coroutineJobsAndCancellation)
        mCorouitneRunBlocking = findViewById(R.id.btn_coroutineRunBlocking)
        mCorouitneWithContext = findViewById(R.id.btn_coroutineWithContext)
        mCorouitneAsyncLaunch = findViewById(R.id.btn_coroutineAsyncLaunch)
        mCorouitneWithRetrofit = findViewById(R.id.btn_coroutineWithRetrofit)
        mCorouitneLazy = findViewById(R.id.btn_coroutineLazy)
        mCorouitneContext = findViewById(R.id.btn_coroutineContext)
    }

    private fun eventHandler(){
        mCoroutineScope.setOnClickListener {
            startActivity(Intent(this,CoroutineScope::class.java))
        }
        mCoroutineJobsAndCancellation.setOnClickListener {
            startActivity(Intent(this,JobAndCancellation::class.java))
        }

        mCorouitneRunBlocking.setOnClickListener {
            startActivity(Intent(this,RunBlocking::class.java))
        }

        mCorouitneWithContext.setOnClickListener {
            startActivity(Intent(this,WithContext::class.java))
        }
        mCorouitneAsyncLaunch.setOnClickListener {
            startActivity(Intent(this,AsyncLaunch::class.java))
        }
        mCorouitneWithRetrofit.setOnClickListener {
            startActivity(Intent(this,CoroutineWithRetrofit::class.java))
        }

        mCorouitneLazy.setOnClickListener {
            startActivity(Intent(this,CoroutineLazy::class.java))
        }
        mCorouitneContext.setOnClickListener {
            startActivity(Intent(this,CoroutineContext::class.java))
        }

    }

    override fun onResume() {
        super.onResume()

       // lightWeightThread()
        //nonLightWeightThread()

    }


    private fun lightWeightThread() {
        val time = measureTime {
            runBlocking {
                repeat(100_000) {
                    launch {

                        Log.d(TAG, "lightWeightThread from ${this.coroutineContext} ${Thread.currentThread()}")
                        delay(5000L)
                       // yield()
                    }
                }
            }
        }
        Log.d(TAG, "it is over in $time")
    }

    private fun nonLightWeightThread() {
        repeat(5000) {
            Thread {
                Thread.sleep(5000L)
                Log.d(TAG, "nonLightWeightThread from ${Thread.currentThread()}")
            }.start()

        }
    }
}