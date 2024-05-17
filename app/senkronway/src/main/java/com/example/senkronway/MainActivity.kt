package com.example.senkronway

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.senkronway.utils.Constants
import java.io.BufferedInputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.time.measureTime

class MainActivity : AppCompatActivity() {
    private lateinit var fetchData: Button
    private lateinit var resultText: TextView
    private lateinit var endPointList: List<String>

    companion object {
        const val TAG = "asenkron"
        const val WIKIPEDIA = "Wikipedia"
        const val STACKOVERFLOW = "Stackoverflow"
        const val MEDIUM = "Medium"
        const val RAYWENDARLICH = "Raywendarlich"
        const val QUORA = "Quora"
        const val UDEMY = "Udemy"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    override fun onResume() {
        super.onResume()
        eventHandler()
    }

    private fun init() {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        fetchData = findViewById(R.id.btn_fetchData)
        resultText = findViewById(R.id.textView)
        endPointList = listOf(MEDIUM, WIKIPEDIA, RAYWENDARLICH, STACKOVERFLOW, QUORA, UDEMY)
    }

    private fun eventHandler() {
        fetchData.setOnClickListener {
            val data = fetchData()
            showData(data)
        }

    }

    private fun showData(data: String) {
        resultText.text = data
    }

    private fun fetchData(): String {
        var mergedData: StringBuilder = StringBuilder()
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
                        Log.d(TAG, "${mergedData.toString()}")
                    }
                } finally {
                    urlConnection.disconnect()
                }
                Log.d(TAG, "$it is finished")
            }
        }
        Log.d(TAG, "download is finished in $time")
        return mergedData.toString()
    }

}