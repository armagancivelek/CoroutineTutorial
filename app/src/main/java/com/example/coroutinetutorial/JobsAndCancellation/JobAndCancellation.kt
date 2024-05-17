package com.example.coroutinetutorial.JobsAndCancellation

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.coroutinetutorial.R
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.yield
import kotlin.coroutines.cancellation.CancellationException

/**
 *
 *     Any kind of exception which may occur in launch scope propagate to parent scope and program will be crashed
 *     Exception does not handle with try-catch. only if the line occuring the exception is getting in to block, exception can be handled
 *     async case is different .  exception being in async arises when we  try to get it.
 *     try -catch is not a good deal for coroutine i because if we got it it still continue to launch
 *     İf one coroutine child is cancelled then all other siblings are cancelled. for the opposite supervisor scope need to be used.
 *     To detect cancellation, the operation must be cooperative.
 *
 *
 *    A cancelled job does not affect other siblings
 *
 *    cancel is also another exception type
 *
 *    Supervisorjob only works if it is the coroutine's direct parent and we need to use handler
 *
 *
 *    Categories of exceptions
 *
 *    Thrown ->launch
 *    Exposed -> async
 *
 *
 */

class JobAndCancellation : AppCompatActivity() {
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_and_cancellation)


        // Supervisor

//        val handler = CoroutineExceptionHandler { context, exception ->
//            Log.d("Corouitine", "exception handler")
//        }
//        val scope = CoroutineScope(SupervisorJob() + CoroutineName("armaan") +  Job())
//        scope.launch(SupervisorJob()) {
//
//            launch {
//                Log.d("Corouitine", "c1 ${Thread.currentThread().name}")
//                delay(3000)
//                throw Exception("exception")
//
//
//            }
//           launch {
//                Log.d("Corouitine", "c2  ${Thread.currentThread().name} ")
//                delay(5000)
//                Log.d(
//                    "Corouitine",
//                    " after c2 ${Thread.currentThread().name}"
//                )
//
//            }
//        }


        // withContext(NonCancallable)  ->we can not launch suspending code in finaly block because coroutine is already cancelled but we really want to launch code in finally block we can use this builder
//         GlobalScope.launch{
//            val job1 = CoroutineScope(Dispatchers.IO).launch {
//                try {
//                    delay(2000)
//                } catch (e: Exception) {
//                    Log.d("Corouitine", e.localizedMessage)
//                } finally {
//
//                        Log.d("Corouitine", "before Cleaned up")
//                        delay(2000)
//                        Log.d("Corouitine", "Cleaned up")
//
//                }
//            }
//            delay(1000)
//            job1.cancel()
//
//        }


        ///Time Out
//                GlobalScope.launch {
//
//                    withTimeout(1000) {  //  one more usage withTimeOutOrNull -> it does not throw error it returns a result
//                        launch {
//                            try {
//                                repeat(100) {
//                                    Log.d("Coroutine", " $it Armagan")
//                                    delay(30)
//                                }
//                            }catch (ex:Exception){
//                                Log.d("Coroutine", " $ex")
//                            }
//
//                        }
//                    }
//                }
    }

    override fun onResume() {
        super.onResume()

        // cooparative

//        GlobalScope.launch {
//            Log.d("Corouitine", "Main program starts ${Thread.currentThread().name} ")
//            val job  = launch {
//                for (i in 0..100000) {
//                 // ensureActive()
//
//                    Log.d("Corouitine", " ${i} ")
//
//                  //   delay(50) // change it with delay
//                }
//            }
//              delay(1000)
//              job.cancel()
//           // job.join()
//            Log.d("Corouitine", "main program ends ${Thread.currentThread().name} ${job} ")
//        }


//         //try-catch
//
//        runBlocking {
//            Log.d("Corouitine", "start" )
//            val job: Job = GlobalScope.launch {
//                launch {
//                    try {
//                        for (i in 1..5000) {
//                            Log.d("Corouitine", "$i ")
//                            delay(50)
//                        }
//                    } catch (ex: CancellationException) {
//                        Log.d("Corouitine", "ex $ex ")
//                    }
//                }
//
//
//            }
//
//            delay(1000)
//            job.cancelAndJoin()
//        }


        // Time out

//        val handler = CoroutineExceptionHandler{_,throwable ->
//            Log.d("Coroutine","caught excepiton :$throwable")// it was only be handled for uncalled exceptions not canlled
//        }
//
//        lifecycleScope.launch(handler) {
//           //Coroutine 2 also cancelled because of parent scope even though it is caught
//            //throw  Exception("Exception")
//            launch {   // change it with async and examine
//                Log.d("Coroutine", "Coroutine 1 is started")
//                delay(300)
//                throw Exception("coroutine 1 failed")
//
//
//            }
//
//            val result = runCatching {
//                Log.d("Coroutine", "runCatching")
//                delay(500)
//                throw Exception("runCatching  failed")
//            }
//
//            if (result.isSuccess) {
//                Log.d("Coroutine", "Success")
//            } else {
//                Log.d("Coroutine", "fail")
//            }
//
//            launch {
//                delay(500)
//                Log.d("Coroutine", "Coroutine 2 is finishedC")
//            }
//
//        }
    }
}