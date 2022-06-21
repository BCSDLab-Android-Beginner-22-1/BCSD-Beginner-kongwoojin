package com.example.myapplication

import android.Manifest
import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.provider.MediaStore
import android.util.Size
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.*
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import java.io.FileNotFoundException
import java.io.InputStream
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            when (isGranted) {
                true -> getAudioFile()
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
            initView()
            hidePermissionSettingsButton()
        }

    lateinit var musicService: MusicService
    private var isBinding = false
    private var isSlideUp = false
    var job: Job? = null

    private var connectionResult = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            isBinding = false
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as MusicService.MusicBinder
            musicService = binder.getService()
            isBinding = true
            initView()
        }
    }

    private val dataList = mutableListOf<MusicData>()
    private val musicAdapter = MusicAdapter()
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyTextView: TextView
    private lateinit var permissionLayout: LinearLayout
    private lateinit var nowPlayingLayout: ConstraintLayout
    private lateinit var nowTitleTextView: TextView
    private lateinit var nowArtistTextView: TextView
    private lateinit var nowAlbumArtImage: ImageView
    private lateinit var playPauseButton: ImageButton
    private lateinit var expandedLayout: ConstraintLayout
    private lateinit var expandedTitleTextView: TextView
    private lateinit var expandedArtistTextView: TextView
    private lateinit var expandedAlbumArtImage: ImageView
    private lateinit var expandedPlayPauseButton: ImageButton
    private lateinit var expandedSeekBar: SeekBar
    private lateinit var expandedPlayTime: TextView
    private lateinit var expandedMusicTime: TextView


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        emptyTextView = findViewById(R.id.empty_text_view)
        recyclerView = findViewById(R.id.recycler_view)
        permissionLayout = findViewById(R.id.permission_layout)

        nowPlayingLayout = findViewById(R.id.now_playing_layout)
        nowAlbumArtImage = findViewById(R.id.now_album_art_image)
        nowTitleTextView = findViewById(R.id.now_title_text_view)
        nowArtistTextView = findViewById(R.id.now_artist_text_view)
        playPauseButton = findViewById(R.id.play_pause_button)

        expandedLayout = findViewById(R.id.expanded_layout)
        expandedTitleTextView = findViewById(R.id.expanded_title_text_view)
        expandedArtistTextView = findViewById(R.id.expanded_artist_text_view)
        expandedAlbumArtImage = findViewById(R.id.expanded_album_art_image)
        expandedPlayPauseButton = findViewById(R.id.expanded_play_pause_button)
        expandedSeekBar = findViewById(R.id.expanded_seek_bar)
        expandedPlayTime = findViewById(R.id.expanded_play_time)
        expandedMusicTime = findViewById(R.id.expanded_music_time)

        val permissionSettingButton: Button = findViewById(R.id.permission_settings_button)

        when (isBinding) {
            true -> initView()
            else -> initService()
        }

        permissionSettingButton.setOnClickListener {
            openSettings.launch(null)
        }

        playPauseButton.setOnClickListener {
            if (isBinding) {
                musicService.playPauseMusic()
                initPlayPauseButton()
            }
        }

        expandedPlayPauseButton.setOnClickListener {
            if (isBinding) {
                musicService.playPauseMusic()
                initPlayPauseButton()
            }
        }

        musicAdapter.setOnClickListener {
            setNowPlaying(it)
        }

        nowPlayingLayout.setOnClickListener {
            val animSlideUp = TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0f,
                Animation.RELATIVE_TO_PARENT, 0f,
                Animation.RELATIVE_TO_PARENT, 1f,
                Animation.RELATIVE_TO_PARENT, 0f
            )
            animSlideUp.duration = 300
            animSlideUp.fillAfter = true

            expandedLayout.animation = animSlideUp
            expandedLayout.visibility = View.VISIBLE
            nowPlayingLayout.visibility = View.GONE

            isSlideUp = true
        }

        nowPlayingLayout.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                v.performClick()
            }
            true
        }

        expandedLayout.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                v.performClick()
            }
            true
        }

        expandedLayout.setOnClickListener {
            val animSlideDown = TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0f,
                Animation.RELATIVE_TO_PARENT, 0f,
                Animation.RELATIVE_TO_PARENT, 0f,
                Animation.RELATIVE_TO_PARENT, 1f
            )
            animSlideDown.duration = 300
            animSlideDown.fillAfter = false

            expandedLayout.animation = animSlideDown
            expandedLayout.visibility = View.GONE
            nowPlayingLayout.visibility = View.VISIBLE
            isSlideUp = false
        }

        expandedSeekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (seekBar != null) {
                    if (fromUser) {
                        expandedPlayTime.text = getDuration(progress.toLong())
                        setMusicPosition(progress)
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                job?.cancel()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (musicService.isPlaying())
                    waitUntilMusicEnd()
            }
        })
    }

    private fun initService() {
        val intent = Intent(this, MusicService::class.java)
        startService(intent)
        bindService(intent, connectionResult, Context.BIND_AUTO_CREATE)
    }

    private fun initView() {
        if (isBinding) {
            if (musicService.isPlaying()) {
                initMusicView()
                waitUntilMusicEnd()
            }
            initPlayPauseButton()
        }

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

    private fun initMusicView() {
        val nowMusic = musicService.nowMusic

        val albumArt = getAlbumArt(this@MainActivity, resources, nowMusic.albumUri.toUri())
        nowAlbumArtImage.setImageDrawable(albumArt)
        expandedAlbumArtImage.setImageDrawable(albumArt)

        nowTitleTextView.text = nowMusic.title
        expandedTitleTextView.text = nowMusic.title
        nowArtistTextView.text = nowMusic.artist
        expandedArtistTextView.text = nowMusic.artist

        expandedMusicTime.text = getDuration(TimeUnit.MILLISECONDS.toSeconds(nowMusic.duration))
    }

    private fun initPlayPauseButton() {
        when (musicService.isPlaying()) {
            true -> {
                playPauseButton.setImageDrawable(
                    AppCompatResources.getDrawable(
                        this,
                        R.drawable.ic_pause
                    )
                )
                expandedPlayPauseButton.setImageDrawable(
                    AppCompatResources.getDrawable(
                        this,
                        R.drawable.ic_pause
                    )
                )
                waitUntilMusicEnd()
            }
            else -> {
                playPauseButton.setImageDrawable(
                    AppCompatResources.getDrawable(
                        this,
                        R.drawable.ic_play
                    )
                )
                expandedPlayPauseButton.setImageDrawable(
                    AppCompatResources.getDrawable(
                        this,
                        R.drawable.ic_play
                    )
                )
                job?.cancel()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkPermission() {
        requestPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun permissionDialog(isDeniedOnce: Boolean) {
        when (isDeniedOnce) {
            true -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle(getString(R.string.dialog_permission_title))
                    .setMessage(getString(R.string.dialog_permission_messsage))
                    .setPositiveButton(getString(R.string.dialog_permission_ok)) { _, _ ->
                        checkPermission()
                    }
                    .setNegativeButton(getString(R.string.dialog_permission_cancel)) { dialog, _ ->
                        dialog.dismiss()
                        showPermissionSettingsButton()
                    }
                    .setCancelable(false)
                builder.show()
            }
            else -> showPermissionSettingsButton()
        }
    }

    private fun showPermissionSettingsButton() {
        recyclerView.visibility = View.GONE
        emptyTextView.visibility = View.GONE
        permissionLayout.visibility = View.VISIBLE
    }

    private fun hidePermissionSettingsButton() {
        recyclerView.visibility = View.VISIBLE
        permissionLayout.visibility = View.GONE
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

                val musicUri = "${MediaStore.Audio.Media.EXTERNAL_CONTENT_URI}/$id"

                val albumUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    "${MediaStore.Audio.Media.EXTERNAL_CONTENT_URI}/$id"
                } else {
                    "content://media/external/audio/albumart/$albumId"
                }

                dataList.add(MusicData(title, artist, duration, musicUri, albumUri))
                musicAdapter.notifyItemInserted(musicAdapter.itemCount)
            }
        }
        checkIsMusicEmpty()
    }

    private fun checkIsMusicEmpty() {
        if (musicAdapter.itemCount != 0) {
            emptyTextView.visibility = View.GONE
        } else {
            emptyTextView.visibility = View.VISIBLE
        }
    }

    private fun setNowPlaying(musicData: MusicData) {
        expandedMusicTime.text = getDuration(TimeUnit.MILLISECONDS.toSeconds(musicData.duration))

        when (isBinding) {
            true -> musicService.startMusic(musicData)
            else -> initService()
        }
        initMusicView()
        initPlayPauseButton()
        waitUntilMusicEnd()
    }

    private fun setMusicPosition(seconds: Int) {
        musicService.updatePosition(seconds)
    }

    private fun waitUntilMusicEnd() {
        val currentPosition = musicService.getCurrentPosition() / 1000
        val seconds =
            TimeUnit.MILLISECONDS.toSeconds(musicService.nowMusic.duration).toInt()

        var nowSeconds = currentPosition
        expandedSeekBar.max = seconds
        expandedSeekBar.progress = currentPosition

        if (job != null && job!!.isActive) {
            job!!.cancel()
        }

        job = CoroutineScope(Dispatchers.Main).launch {
            repeat(seconds - currentPosition + 1) {
                delay(1000)
                nowSeconds++
                expandedSeekBar.incrementProgressBy(1)
                expandedPlayTime.text = getDuration(nowSeconds.toLong())
            }
            initPlayPauseButton()
        }
    }

    private fun getAlbumArt(context: Context, resources: Resources, albumUri: Uri): Drawable? {
        var inputStream: InputStream? = null
        val albumArt: Drawable? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            try {
                BitmapDrawable(
                    resources,
                    context.contentResolver.loadThumbnail(albumUri, Size(500, 500), null)
                )
            } catch (e: FileNotFoundException) {
                ResourcesCompat.getDrawable(resources, R.drawable.ic_no_album_art, null)
            }
        } else {
            try {
                inputStream = context.contentResolver.openInputStream(albumUri)
                val option = BitmapFactory.Options()
                option.outWidth = 500
                option.outHeight = 500
                option.inSampleSize = 2
                BitmapDrawable(resources, BitmapFactory.decodeStream(inputStream, null, option))
            } catch (e: FileNotFoundException) {
                ResourcesCompat.getDrawable(resources, R.drawable.ic_no_album_art, null)
            }
        }
        inputStream?.close()

        return albumArt
    }

    private fun getDuration(seconds: Long): String {
        val hours = TimeUnit.SECONDS.toHours(seconds)
        val minutes =
            TimeUnit.SECONDS.toMinutes(seconds) - TimeUnit.HOURS.toMinutes(hours)
        val newSeconds =
            seconds - TimeUnit.HOURS.toSeconds(hours) - TimeUnit.MINUTES.toSeconds(minutes)

        val duration = if (hours.toInt() != 0) {
            String.format("%02d:%02d:%02d", hours, minutes, newSeconds)
        } else {
            String.format("%02d:%02d", minutes, newSeconds)
        }
        return duration
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
        if (isBinding)
            if (!musicService.isPlaying()) {
                musicService.killService()
            }
        unbindService(connectionResult)
        isBinding = false
    }
}