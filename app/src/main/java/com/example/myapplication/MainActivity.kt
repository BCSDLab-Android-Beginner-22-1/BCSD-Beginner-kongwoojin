package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

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

        val dialogButton: Button = findViewById(R.id.dialog_button)
        val countButton: Button = findViewById(R.id.count_button)
        val randomButton: Button = findViewById(R.id.random_button)
        countTextView = findViewById(R.id.count_text_view)

        countTextView.text = currentCount.toString()

        dialogButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(getString(R.string.dialog_title))
                .setMessage(getString(R.string.dialog_text))
                .setPositiveButton(getString(R.string.dialog_reset)) { _, _ ->
                    currentCount = 0
                    countTextView.text = currentCount.toString()
                }
                .setNegativeButton(getString(R.string.dialog_dismiss)) { dialog, _ ->
                    dialog.dismiss()
                }
                .setNeutralButton(getString(R.string.dialog_toast)) { _, _ ->
                    Toast.makeText(this, getString(R.string.toast_message), Toast.LENGTH_SHORT)
                        .show()
                }
            builder.show()
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