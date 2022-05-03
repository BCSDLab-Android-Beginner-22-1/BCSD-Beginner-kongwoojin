package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class SubActivity : AppCompatActivity(), CountData {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub)

        val countFromMain = intent.getIntExtra("count", 0)

        val fragment: Fragment = SubFragment()

        val bundle = Bundle().apply {
            putInt("count", countFromMain)
        }

        fragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

    override fun countData(randomNumber: Int) {
        returnData(randomNumber)
    }

    private fun returnData(randomNumber: Int) {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("count", randomNumber)
        }
        setResult(RESULT_OK, intent)
    }
}