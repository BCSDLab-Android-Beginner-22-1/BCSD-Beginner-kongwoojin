package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    var lapList = mutableListOf<LapTime>()
    private var job: Job? = null
    private var time = 0L
    private var isTimerRunning = false
    private val lapTimeAdapter = LapTimeAdapter()
    private var lapItemCount = 1

    private lateinit var timeTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timeTextView = findViewById(R.id.time_text_view)
        val startPauseButton: Button = findViewById(R.id.start_pause_button)
        val stopButton: Button = findViewById(R.id.stop_button)
        val lapButton: Button = findViewById(R.id.lap_button)
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)

        startPauseButton.setOnClickListener {
            isTimerRunning = when (isTimerRunning) {
                true -> {
                    job!!.cancel()
                    startPauseButton.text = getString(R.string.timer_start)
                    false
                }
                else -> {
                    timer()
                    startPauseButton.text = getString(R.string.timer_pause)
                    true
                }
            }
        }

        stopButton.setOnClickListener {
            job?.cancel()
            time = 0L
            startPauseButton.text = getString(R.string.timer_start)
            lapList.clear()
            lapItemCount = 1
            lapTimeAdapter.notifyDataSetChanged()
            timeTextView.text = getString(R.string.timer_time_placeholder)
            isTimerRunning = false
        }

        lapButton.setOnClickListener {
            if (isTimerRunning) {
                lapList.add(0, LapTime(lapItemCount, time))
                lapItemCount++
                lapTimeAdapter.notifyItemInserted(0)
                recyclerView.scrollToPosition(0)
            }
        }

        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            LinearLayoutManager(this).orientation
        )

        lapTimeAdapter.lapList = lapList
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = lapTimeAdapter
            addItemDecoration(dividerItemDecoration)
        }
    }

    private fun timer() {
        isTimerRunning = true

        var delayTime = 10L
        var prevSystemMillis = System.currentTimeMillis()
        var curSystemMillis: Long
        var timeError: Long
        job = CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                delay(delayTime)

                // Time error correction
                curSystemMillis = System.currentTimeMillis()
                timeError = curSystemMillis - 10L - prevSystemMillis
                delayTime -= timeError
                prevSystemMillis = curSystemMillis

                timeTextView.text = convertTime(time)
                time += 10
            }
        }
    }

    private fun convertTime(time: Long): String {
        val nowMinutes = TimeUnit.MILLISECONDS.toMinutes(time)
        val nowSeconds =
            TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(nowMinutes)
        val nowMillis =
            time - TimeUnit.SECONDS.toMillis(nowSeconds) - TimeUnit.MINUTES.toMillis(nowMinutes)

        return String.format("%02d:%02d.%02d", nowMinutes, nowSeconds, nowMillis / 10)
    }
}