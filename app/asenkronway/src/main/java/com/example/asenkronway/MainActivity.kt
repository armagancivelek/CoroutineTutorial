package com.example.asenkronway

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.asenkronway.model.Post
import com.example.asenkronway.utils.Constants
import java.io.BufferedInputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.math.BigInteger
import java.net.HttpURLConnection
import java.net.URL
import java.util.Random
import java.util.concurrent.Callable
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors
import java.util.concurrent.Future
import kotlin.time.measureTime

class MainActivity : AppCompatActivity() {
    private lateinit var fetchData: Button
    private lateinit var postItem: Button
    private lateinit var postFuture: Button
    private lateinit var resultText: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var endPointList: List<String>

    companion object {
        const val TAG = "AsenkronWay"
        const val WIKIPEDIA = "Wikipedia"
        const val STACKOVERFLOW = "Stackoverflow"
        const val MEDIUM = "Medium"
        const val RAYWENDARLICH = "Raywendarlich"
        const val UDEMY = "Udemy"
        const val QUORA = "Quora"
        const val TOKEN = "Token"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

    }

    override fun onResume() {
        super.onResume()
        eventHandler()

        val executor = Executors.newFixedThreadPool(2)
        val myFuture: Future<String> = executor.submit(Callable<String> {
            Thread.sleep(2000)
            "armagan"
        })

        myFuture.get()

    }

    private fun init() {
        fetchData = findViewById(R.id.btn_fetchData)
        postItem = findViewById(R.id.btn_postItem)
        postFuture = findViewById(R.id.btn_postItemWithFuture)
        resultText = findViewById(R.id.textView)
        progressBar = findViewById(R.id.progressBar)
        endPointList = listOf(MEDIUM, WIKIPEDIA, RAYWENDARLICH, STACKOVERFLOW, QUORA, UDEMY)
    }

    private fun eventHandler() {

        fetchData.setOnClickListener {
            fetchData { data ->
                showData(data)
            }
        }

        postItem.setOnClickListener {
            requestToken("armagancivelek", "1234567") { token ->
                preparePost(token) { post ->
                    submitPost(post)
                }
            }
        }

        postFuture.setOnClickListener {
            requestTokenWithFuture("armagan", "1234567")
                .thenCompose { token -> preparePostWithFuture(token) }
                .thenAccept { post -> submitPost(post) }
        }


    }

    private fun showData(data: String) {
        resultText.text = data
    }

    private fun requestTokenWithFuture(id: String, pass: String): CompletableFuture<String> {

        val cores = Runtime.getRuntime().availableProcessors()
        val executorService = Executors.newFixedThreadPool(cores)
        return CompletableFuture.supplyAsync({
            fetchSingleData(TOKEN)
        }, executorService)

    }

    private fun preparePostWithFuture(token: String): CompletableFuture<Post> {
        showProgress("preparing post...")
        Log.d(TAG, "Prepare post is started")
        return CompletableFuture.supplyAsync {
            val result = BigInteger.probablePrime(2000, Random())
            Post(token.length + 1, "armagancivelek", "hello world", result.toString())
        }

    }

    private fun fetchSingleData(endPoint: String): String {
        var result: StringBuilder = StringBuilder()
        val url = URL("${Constants.BASE_URL}/$endPoint")
        val urlConnection = url.openConnection() as HttpURLConnection
        try {
            val inputStream: InputStream =
                BufferedInputStream(urlConnection.inputStream)
            val isw = InputStreamReader(inputStream)
            var data = isw.read()
            while (data != -1) {
                result.append(data.toChar())
                data = isw.read()
            }
        } catch (e: Exception) {
            Log.d(TAG, "Exception : ${e.message} ")
            hideProgress()
        } finally {
            urlConnection.disconnect()

        }

        return result.toString()
    }


    private fun fetchData(result: (String) -> Unit) {
        Thread {
            showProgress("fetching...")
            var mergedData: StringBuilder = StringBuilder("")
            Log.d(TAG, "download is starting")
            val time = measureTime {
                endPointList.forEach {
                    Log.d(TAG, "$it downloading is starting")
                    val url = URL("${Constants.BASE_URL}/$it")
                    val urlConnection = url.openConnection() as HttpURLConnection
                    try {
                        val inputStream: InputStream =
                            BufferedInputStream(urlConnection.inputStream)
                        val isw = InputStreamReader(inputStream)
                        var data = isw.read()
                        while (data != -1) {
                            mergedData.append(data.toChar())
                            data = isw.read()
                            Log.d(TAG, mergedData.toString())
                        }
                    } catch (e: Exception) {
                        Log.d(TAG, "Exception : ${e.message} ")
                        hideProgress()
                    } finally {
                        urlConnection.disconnect()

                    }
                    Log.d(TAG, "$it is finished")
                }
            }
            Log.d(TAG, "download is finished in $time")
            hideProgress()

                result.invoke(mergedData.toString())

        }.start()
    }

    private fun requestToken(id: String, pass: String, result: (String) -> Unit) {
        Thread {
            showProgress("getting token...")
            var resultData: StringBuilder = StringBuilder()
            Log.d(TAG, "download is starting")
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
                        Log.d(TAG, resultData.toString())
                    }
                } catch (e: Exception) {
                    hideProgress()
                    Log.d(TAG, "Exception : ${e.message} ")
                } finally {
                    urlConnection.disconnect()
                }
            }
            Log.d(TAG, "download is finished in $time")
            result.invoke(resultData.toString())
            hideProgress()
        }.start()

    }

    private fun showProgress(message: String) {
        runOnUiThread {
            resultText.text = message
            progressBar.visibility = View.VISIBLE
        }
    }

    private fun hideProgress() {
        runOnUiThread {
            progressBar.visibility = View.INVISIBLE
        }
    }

    private fun preparePost(token: String, submit: (Post) -> Unit) {
        Thread {
            showProgress("preparing post...")
            val time = measureTime {
                Log.d(TAG, "Prepare post is started")
                val result = BigInteger.probablePrime(2000, Random())
                val myPost =
                    Post(token.length + 1, "armagancivelek", "hello world", result.toString())
                submit.invoke(myPost)
            }
            hideProgress()
            Log.d(TAG, "Prepare post is done in $time")
        }.start()
    }

    private fun submitPost(post: Post) {
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
                resultText.text = "Posted"
            } else {
                resultText.text = "Fail"
            }

        } catch (e: Exception) {
            Log.d(TAG, "Exception : ${e.message} ")
        } finally {
            urlConnection.disconnect()
        }
    }
}

