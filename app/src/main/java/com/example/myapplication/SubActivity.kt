package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlin.random.Random

class SubActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub)

        val countFromMain = intent.getIntExtra("count", 0)
        val randomNumber = Random.nextInt(countFromMain + 1)

        val currentNumTextView: TextView = findViewById(R.id.current_num_text_view)
        val infoTextView: TextView = findViewById(R.id.info_text_view)

        infoTextView.text = String.format(getString(R.string.random_info), countFromMain)

        currentNumTextView.text = randomNumber.toString()

        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("count", randomNumber)
        }
        setResult(RESULT_OK, intent)
    }
}