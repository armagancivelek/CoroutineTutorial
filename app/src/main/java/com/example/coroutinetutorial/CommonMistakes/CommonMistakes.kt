package com.example.coroutinetutorial.CommonMistakes

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

//Mistake 1
suspend fun getUserFirstNames(userIds: List<String>): List<String> {

    val firstNames = mutableListOf<String>()

    for (id in userIds) {
        firstNames.add(getFirstName(id))
    }

    return firstNames
}

suspend fun getFirstName(id: String): String {

    delay(200)
    return "First Name"

}


//Mistake 2

suspend fun doHeavyWork() {
    val job = CoroutineScope(Dispatchers.Default).launch {
        var random = Random.nextInt(1000_000)
        while (random != 50000) {
            random = Random.nextInt(100_000)
        }
    }

    delay(5000)
    job.cancel()
}


 // Mistake 3

suspend fun  riskyTask(){  // propagate problem
     try {
         delay(3000)
         Log.d("Coroutine", "The answer is ${10/0}")
     } catch (e :Exception){
         Log.d("Coroutine", "exception is $e")
     }
}


   //Mistake 4

 class  MainViewModel : ViewModel() {

     suspend fun  postValueApi(){
         delay(300)
     }
 }

class MyFragment : Fragment() {
    private val viewModel : MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.postValueApi()
        }
    }


}
