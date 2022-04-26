package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    var currentCount = 0

    private lateinit var countTextView: TextView

    private val startActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            currentCount = it.data!!.getIntExtra("count", 0)
            countTextView.text = currentCount.toString()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toastButton: Button = findViewById(R.id.toast_button)
        val countButton: Button = findViewById(R.id.count_button)
        val randomButton: Button = findViewById(R.id.random_button)
        countTextView = findViewById(R.id.count_text_view)

        countTextView.text = currentCount.toString()

        toastButton.setOnClickListener {
            Toast.makeText(this, getString(R.string.toast_message), Toast.LENGTH_SHORT).show()
        }

        countButton.setOnClickListener {
            currentCount += 1
            countTextView.text = currentCount.toString()
        }

        randomButton.setOnClickListener {
            val intent = Intent(this, SubActivity::class.java)
            intent.putExtra("count", currentCount)
            startActivity.launch(intent)
        }
    }
}