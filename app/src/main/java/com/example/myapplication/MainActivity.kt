package com.example.myapplication

import android.Manifest
import android.content.ContentUris
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.View
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                getAudioFile()
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                permissionDialog(true)
            } else {
                permissionDialog(false)
            }
        }

    private val dataList = mutableListOf<MusicData>()
    private val musicAdapter = MusicAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            LinearLayoutManager(this).orientation
        )

        musicAdapter.dataList = dataList
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = musicAdapter
            addItemDecoration(dividerItemDecoration)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission()
        } else {
            getAudioFile()
        }
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
                    // Open application settings
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri: Uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                }

            }
            .setNegativeButton(getString(R.string.dialog_permission_cancel)) { dialog, _ ->
                dialog.dismiss()
                finish()
            }
            .setCancelable(false)
        builder.show()
    }

    private fun getAudioFile() {
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DURATION
        )

        val sortOrder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            "${MediaStore.Files.FileColumns.ALBUM}, ${MediaStore.Files.FileColumns.ARTIST}, CAST(${MediaStore.Files.FileColumns.CD_TRACK_NUMBER} AS INTEGER)"
        } else {
            " ${MediaStore.Audio.AlbumColumns.ALBUM}, ${MediaStore.Audio.AlbumColumns.ARTIST}, CAST(${MediaStore.Audio.AudioColumns.TRACK} AS INTEGER)"
        }
        val cursor = this.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            MediaStore.Audio.Media.IS_MUSIC,
            null,
            sortOrder
        )

        cursor?.use {
            val idColumn = cursor.getColumnIndex(MediaStore.Audio.Media._ID)
            val albumIdColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)
            val titleColumn = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val artistColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
            val durationColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val albumId = cursor.getLong(albumIdColumn)
                val title = cursor.getString(titleColumn)
                val artist = cursor.getString(artistColumn)
                val duration = cursor.getLong(durationColumn)

                val albumUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    ContentUris.withAppendedId(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        id
                    )
                } else {
                    ContentUris.withAppendedId(
                        Uri.parse("content://media/external/audio/albumart"),
                        albumId
                    )
                }

                dataList.add(MusicData(title, artist, duration, albumUri))
                musicAdapter.notifyItemInserted(musicAdapter.itemCount)
            }
        }
        checkIsMusicEmpty()
    }

    private fun checkIsMusicEmpty() {
        if (musicAdapter.itemCount != 0) {
            val emptyTextView: TextView = findViewById(R.id.empty_text_view)
            emptyTextView.visibility = View.GONE
        }
    }
}