package com.example.myapplication.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityWriteBinding
import com.example.myapplication.viewmodel.WriteViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class WriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWriteBinding
    private val writeViewModel: WriteViewModel by viewModel()

    private val startActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val imageUri = it.data!!.getStringExtra("uri")
                writeViewModel.setImageUri(imageUri)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_write)

        writeViewModel.setArticleData(intent.getIntExtra("position", -1))

        binding.activity = this
        binding.vm = writeViewModel
        binding.lifecycleOwner = this

        val intent = Intent(this, MainActivity::class.java)
        setResult(RESULT_OK, intent)
    }

    fun addArticle() {
        writeViewModel.addData()
        finish()
    }

    fun getImageList() {
        val intent = Intent(this, ImageChooseActivity::class.java)
        startActivity.launch(intent)
    }
}