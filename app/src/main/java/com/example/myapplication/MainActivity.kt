package com.example.myapplication

import android.Manifest
import android.content.ContentUris
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Size
import android.view.View
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.FileNotFoundException
import java.io.InputStream

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission()
        } else {
            getAudioFile()
        }

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)

        musicAdapter.dataList = dataList
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = musicAdapter
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
            "${MediaStore.Files.FileColumns.ARTIST}, ${MediaStore.Files.FileColumns.ALBUM}, CAST(${MediaStore.Files.FileColumns.CD_TRACK_NUMBER} AS INTEGER)"
        } else {
            "${MediaStore.Audio.AlbumColumns.ARTIST}, ${MediaStore.Audio.AlbumColumns.ALBUM}, CAST(${MediaStore.Audio.AudioColumns.TRACK} AS INTEGER)"
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
                val uri = ContentUris.withAppendedId(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    id
                )

                var inputStream: InputStream? = null
                val albumArt: Bitmap? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    try {
                        this.contentResolver.loadThumbnail(uri, Size(200, 200), null)
                    } catch (e: FileNotFoundException) {
                        null
                    }
                } else {
                    val albumUri = ContentUris.withAppendedId(
                        Uri.parse("content://media/external/audio/albumart"),
                        albumId
                    )
                    try {
                        inputStream = this.contentResolver.openInputStream(albumUri)
                        val option = BitmapFactory.Options()
                        option.outWidth = 200
                        option.outHeight = 200
                        option.inSampleSize = 2
                        BitmapFactory.decodeStream(inputStream, null, option)
                    } catch (e: FileNotFoundException) {
                        null
                    }
                }
                inputStream?.close()

                dataList.add(MusicData(title, artist, duration, albumArt))
                musicAdapter.notifyItemInserted(musicAdapter.itemCount)
            }
        }
        checkIsMusicEmpty()
    }

    private fun checkIsMusicEmpty() {
        /* musicAdapter.itemCount always return 0 on api 22.
        * So, get item size from dataList on under api 23 */
        
        val musicItems = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            dataList.size
        } else {
            musicAdapter.itemCount
        }

        if (musicItems != 0) {
            val emptyTextView: TextView = findViewById(R.id.empty_text_view)
            emptyTextView.visibility = View.GONE
        }
    }
}