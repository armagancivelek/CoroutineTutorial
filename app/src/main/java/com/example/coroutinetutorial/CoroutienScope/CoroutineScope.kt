package com.example.coroutinetutorial.CoroutienScope

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.coroutinetutorial.R
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 *
 *   !Every coroutine needs to start within coroutine scope, we can not launch coroutine without coroutine scope
 *   !Coroutine scope is a way to keep track of all coroutines that runs inside it
 *   !CoroutineScope takes context as a parameter, this context is a set of various element ;
 *      Job + Dispatcher + CoroutineName + Exception Handler
 *   ! Parent coroutine is cancelled, all its children are cancelled as well
 *   ! Parent coroutine always waits for all its children to complete
 *   ! Global Scope is used to launch a top level coroutine, it lives as long as application live
 *   ! As best practise each coroutine scope should be tied to a specific application component lifecycle
 *   ! android gives us some predefined scopes :
 *       lifecycleScope : it can be used from activities and fragments and it is tied to their life cycles
 *       viewModelScope : iy is used in viewmodel and it is tied to viewmodel lifecycle scope
 *       so thanks this scopes, we do not have to worry about memory leaks anymore
 *
 *
 *
 */
class CoroutineScope : AppCompatActivity() {
    companion object {
        val TAG = "CoroutineScope"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_coroutine_scope)


//                GlobalScope.launch() {
//                    Log.d(TAG,"Coroutine says hello from thread: ${Thread.currentThread().name}")
//                }
//                Log.d(TAG,"hello from thread: ${Thread.currentThread().name}")
    }
}