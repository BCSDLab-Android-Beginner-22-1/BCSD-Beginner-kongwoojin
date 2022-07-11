package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.myapplication.ViewModelSingleton.viewModel
import com.example.myapplication.databinding.ActivityWriteBinding

class WriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWriteBinding

    private var position = -1

    private lateinit var article: Article

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_write)

        if (intent.extras != null) {
            position = intent.getIntExtra("position", 0)
            article = viewModel.getData(position)
        } else {
            article = Article("", "", "", "")
        }
        binding.article = article
        binding.activity = this
    }

    fun addArticle() {
        viewModel.addData(article, position)
        finish()
    }
}