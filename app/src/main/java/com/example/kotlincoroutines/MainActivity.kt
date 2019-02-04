package com.example.kotlincoroutines

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlincoroutines.network.RequestService
import com.example.kotlincoroutines.network.Result
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {


    /**
     * Dispatch Job Instance
     */
    private var codeStandardJob: Job = Job()

    /**
     *  launch scope
     */
    private val uiScope = CoroutineScope(Dispatchers.Main + codeStandardJob)

    /**
     * Request Service Instance
     */
    private val requestService: RequestService = RequestService(this@MainActivity)

    /**
     * RecyclerView Adapter
     */
    private val codeAdapter = CodeAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        initUI()
        letsMakeAPICallUsingCoroutine()
    }

    /**
     * Initialise RecyclerView
     */
    private fun initUI() {
        codeRecyclerView.setHasFixedSize(true)
        codeRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false) as RecyclerView.LayoutManager?
        codeRecyclerView.adapter = codeAdapter
    }

    override fun onDestroy() {

        // Destroy job one activity lifecycle is over
        codeStandardJob.cancel()
        super.onDestroy()
    }

    /**
     * Make API Call With Help of Kotlin CoRoutine
     */
    private fun letsMakeAPICallUsingCoroutine() {
        codeStandardJob = uiScope.launch {
            val result = requestService.getCodeStandards()
            when (result) {
                is Result.Success -> {
                    codeAdapter.updateData(result.data.data)
                } // Do Something }
                is Result.Error -> {
                    Log.e("MainActivity", result.exception.toString())
                } // Log Error
            }
        }
    }
}

