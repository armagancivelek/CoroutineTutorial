package com.example.coroutinetutorial.CoroutineWithRetrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.coroutinetutorial.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory


const val BASE_URL = "https://jsonplaceholder.typicode.com/"

class CoroutineWithRetrofit : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine_with_retrofit)


        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyAPI::class.java)

//
//                api.getComments().enqueue(object : Callback<List<Comment>>{ //enque start it in another thread
//            override fun onResponse(p0: Call<List<Comment>>, response: Response<List<Comment>>) {
//             if (response.isSuccessful){
//                 response.body()?.let {
//                     for (comment in it) {
//                         Log.d("Coroutine",comment.toString())
//                     }
//                 }
//             }
//            }
//
//            override fun onFailure(p0: Call<List<Comment>>, p1: Throwable) {
//                Log.e("Corouitine","Error $p1")
//            }
//
//        })

/*                GlobalScope.launch (Dispatchers.IO){
            val comments = api.getComments().await() // it gives us directlye list of comments in any error case this is useless
            for(comment in comments) {
                Log.d("Coroutine",comment.toString())
            }
        }*/
/*
                GlobalScope.launch (Dispatchers.IO){
            val response = api.getComments().awaitResponse() // it gives us error handling away
            if (response.isSuccessful){
                for(comment in response.body()!!) {
                    Log.d("Coroutine",comment.toString())
                }
            }
        }*/

//        GlobalScope.launch(Dispatchers.IO) {
//            val response = api.getComments()
//            if (response.isSuccessful) {
//                for (comment in response.body()!!) {
//                    Log.d("Coroutine", comment.toString())
//                }
//            }
//            Log.d("Coroutine","it is done")
//        }
    }
}