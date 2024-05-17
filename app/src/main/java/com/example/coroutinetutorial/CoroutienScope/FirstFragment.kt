package com.example.coroutinetutorial.CoroutienScope

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.coroutinetutorial.R
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FirstFragment : Fragment() {
    private lateinit var goSecondPage : Button

    private val scope  = CoroutineScope(Dispatchers.Default + CoroutineName("Armagan"))
    private lateinit var  globalJob: Job;




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_first, container, false)

        goSecondPage = view.findViewById(R.id.btn_go_secondFragment)



/*       val job = scope.launch {
            Log.d("Coroutine",this.coroutineContext.toString()+"Thead : ${Thread.currentThread().name}")
            launch {
                Log.d("Coroutine",this.coroutineContext.toString() +"Thead : ${Thread.currentThread().name}")
            }
            while(true) {
                Log.d("Coroutine","corouitne is running inside my scope")
                delay(1000)
            }
        }*/

        scope.launch {
            while(true) {
                Log.d("Coroutine","corouitne scope 1 is running inside global scope")
                delay(1000)
            }
        }

        scope.launch {
            while(true) {
                Log.d("Coroutine","corouitne scope 2 is running inside global scope")
                delay(1000)
            }
        }
/*

         globalJob = GlobalScope.launch { // parent
            Log.d("Coroutine",this.coroutineContext.toString()+"Thead : ${Thread.currentThread().name}")
            launch { //child
                Log.d("Coroutine",this.coroutineContext.toString() +"Thead : ${Thread.currentThread().name}")
                while(true) {
                    Log.d("Coroutine","corouitne is running inside global scope")
                    delay(1000)
                }
            }
             launch {

             }
             launch {

             }
            while(true) {
                Log.d("Coroutine","corouitne is running inside global scope")
                delay(1000)
            }
        }
*/




        goSecondPage.setOnClickListener {
              activity?.let{
                  it.supportFragmentManager.beginTransaction().setReorderingAllowed(true)
                      .replace(R.id.fragment_container_view,SecondFragment()).commit()
              }
        }


        return view
    }


    override fun onStop() {
        super.onStop()
        Log.d("Coroutine","onStop from $this")
    }

    override fun onDestroy() {
        super.onDestroy()
       // globalJob.cancel("Global joc is cancelled")
        scope.cancel("Scope lifecycle is over")

        Log.d("Coroutine","onDestroy from $this")
    }
}