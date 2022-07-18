package com.example.myapplication.view

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapter.ImageListAdapter
import com.example.myapplication.databinding.ActivityImageChooseBinding
import com.example.myapplication.util.OpenSettings
import com.example.myapplication.viewmodel.ImageViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ImageChooseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImageChooseBinding
    private val imageViewModel: ImageViewModel by viewModel()

    private val imageListAdapter = ImageListAdapter()

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            when (isGranted) {
                true -> loadImageList()
                else -> {
                    when (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        true -> permissionDialog(true)
                        else -> permissionDialog(false)
                    }
                }
            }
        }

    private val openSettings =
        registerForActivityResult(OpenSettings()) {
            checkPermission()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_image_choose)

        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 5)
            adapter = imageListAdapter
        }

        imageListAdapter.setOnClickListener {
            val intent = Intent(this, WriteActivity::class.java)
            intent.putExtra("uri", imageViewModel.getImageUri(it))
            setResult(RESULT_OK, intent)
            finish()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission()
        } else {
            loadImageList()
        }

        binding.vm = imageViewModel
        binding.lifecycleOwner = this
    }

    private fun loadImageList() {
        imageViewModel.loadImageList()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkPermission() {
        requestPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun permissionDialog(isDeniedOnce: Boolean) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.dialog_permission_title))
            .setMessage(getString(R.string.dialog_permission_messsage))
            .setPositiveButton(getString(R.string.dialog_permission_ok)) { _, _ ->
                if (isDeniedOnce) {
                    checkPermission()
                } else {
                    openSettings.launch(null)
                }
            }
            .setNegativeButton(getString(R.string.dialog_permission_cancel)) { dialog, _ ->
                dialog.dismiss()
                finish()
            }
        builder.show()
    }
}