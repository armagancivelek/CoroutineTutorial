package com.example.coroutineway

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.coroutineway.model.Post
import com.example.coroutineway.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.math.BigInteger
import java.net.HttpURLConnection
import java.net.URL
import java.util.Random
import kotlin.time.measureTime

class MainActivity : AppCompatActivity() {

    private lateinit var postData: Button
    private lateinit var resultText: TextView
    private lateinit var progressBar: ProgressBar

    companion object {
        const val TAG = "AsenkronWay"
        const val TOKEN = "Token"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        eventHandler()

    }


    private fun init() {
        postData = findViewById(R.id.btn_postData)
        resultText = findViewById(R.id.textView)
        progressBar = findViewById(R.id.progressBar)
    }

    private fun eventHandler() {
        postData.setOnClickListener {
            lifecycleScope.launch {
                Log.d(TAG,"current Thread:${Thread.currentThread()}")
                withContext(Dispatchers.IO) {
                    Log.d(TAG,"current Thread:${Thread.currentThread()}")
                    val token = requestToken("armagan", "1234567")
                    val post = preparePost(token)
                    submitPost(post)
                }
            }
        }
    }

    private suspend fun submitPost(post: Post) {
        val url = URL("${Constants.BASE_URL}/POST")
        val urlConnection = url.openConnection() as HttpURLConnection
        urlConnection.apply {
            setRequestMethod("POST")
            setRequestProperty("Accept", "application/json")
            doOutput = true
        }
        try {
            val outputStream: OutputStream = urlConnection.outputStream
            with(outputStream) {
                write(post.toString().toByteArray())
                flush()
                close()
            }
            val responseCode: Int = urlConnection.getResponseCode()
            Log.d(TAG, "Response Code: $responseCode")
            if (responseCode == HttpURLConnection.HTTP_CREATED) {
                withContext(Dispatchers.Main) {
                    resultText.text = "Posted"
                }
            } else {
                withContext(Dispatchers.Main) {
                    resultText.text = "Fail"
                }
            }

        } catch (e: Exception) {
            Log.d(TAG, "Exception : ${e.message} ")
        } finally {
            urlConnection.disconnect()
            hideProgress()
        }
    }

    private suspend fun requestToken(id: String, pass: String): String {
          showProgress("getting token")
        var resultData: StringBuilder = StringBuilder()
        Log.d(TAG, "$TOKEN download is starting in ${Thread.currentThread()}")
        val time = measureTime {
            val url = URL("${Constants.BASE_URL}/$TOKEN")
            val urlConnection = url.openConnection() as HttpURLConnection
            try {
                val inputStream: InputStream = BufferedInputStream(urlConnection.inputStream)
                val isw = InputStreamReader(inputStream)
                var data = isw.read()
                while (data != -1) {
                    resultData.append(data.toChar())
                    data = isw.read()
                //    Log.d(TAG, resultData.toString())
                }
            } catch (e: Exception) {
                Log.d(TAG, "Exception : ${e.message} ")
            } finally {
                urlConnection.disconnect()
                  hideProgress()
            }
        }
        Log.d(TAG, "$TOKEN download is finished in $time")
        return resultData.toString()
    }


    private suspend fun preparePost(token: String): Post {
        showProgress("preparing post... ")
        Log.d(TAG, "Prepare post is started in ${Thread.currentThread()}")
        val result = BigInteger.probablePrime(2000, Random())
        val post = Post(token.length + 1, "armagancivelek", "hello world", result.toString())
        Log.d(TAG, "Prepare post is done ")
        hideProgress()
        return post;


    }

    private suspend fun showProgress(message: String) {
        withContext(Dispatchers.Main) {
            resultText.text = message
            progressBar.visibility = View.VISIBLE
        }
    }

    private suspend fun hideProgress() {
        withContext(Dispatchers.Main) {
            progressBar.visibility = View.INVISIBLE
        }
    }
}



