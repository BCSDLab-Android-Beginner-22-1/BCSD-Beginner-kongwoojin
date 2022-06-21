package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Size
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.net.toUri
import kotlinx.coroutines.*
import java.io.FileNotFoundException
import java.io.InputStream
import java.util.concurrent.TimeUnit

class MusicService : Service() {

    companion object {
        const val CHANNEL_ID = "notification_music"
        const val notificationId = 1000
    }

    private val binder = MusicBinder()
    private lateinit var mediaPlayer: MediaPlayer
    var job: Job? = null
    lateinit var nowMusic: MusicData

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        return START_STICKY
    }

    inner class MusicBinder : Binder() {
        fun getService(): MusicService {
            return this@MusicService
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "MusicService"
            val descriptionText = "MusicService"
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(musicData: MusicData) {
        val image: Bitmap = getAlbumArt(this, resources, musicData.albumUri.toUri())
        val intent = Intent(this, MainActivity::class.java)

        val pendingIntent: PendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        } else {
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification_music)
            .setLargeIcon(image)
            .setContentTitle(musicData.title)
            .setContentText(musicData.artist)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT) // Need for api <= 25
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)) {
            notify(notificationId, builder.build())
        }
        startForeground(notificationId, builder.build())
    }

    fun startMusic(musicData: MusicData) {
        nowMusic = musicData
        if (this::mediaPlayer.isInitialized && isPlaying())
            stopMusic()
        createNotification(musicData)
        mediaPlayer = MediaPlayer().apply {
            setAudioStreamType(AudioManager.STREAM_MUSIC)
            setDataSource(applicationContext, musicData.musicUri.toUri())
            prepare()
            start()
        }
        waitUntilMusicEnd(musicData.duration)
    }

    private fun stopMusic() {
        mediaPlayer.apply {
            stop()
        }
    }

    fun isPlaying(): Boolean {
        return if (isMediaPlayerInitialized())
            mediaPlayer.isPlaying
        else
            false
    }

    private fun isMediaPlayerInitialized(): Boolean {
        return this::mediaPlayer.isInitialized
    }

    fun playPauseMusic() {
        if (isMediaPlayerInitialized()) {
            when (isPlaying()) {
                true -> mediaPlayer.pause()
                else -> mediaPlayer.start()
            }
        }
    }

    private fun waitUntilMusicEnd(duration: Long) {
        val seconds = TimeUnit.MILLISECONDS.toSeconds(duration)

        if (job != null && job!!.isActive) {
            job!!.cancel()
        }

        job = CoroutineScope(Dispatchers.Main).launch {
            repeat(seconds.toInt() + 1) {
                delay(1000)
            }
            if (!isPlaying()) {
                killService()
            }
        }
    }

    fun killService() {
        stopForeground(true)
        stopSelf()
    }

    fun getCurrentPosition(): Int {
        return mediaPlayer.currentPosition
    }

    private fun getAlbumArt(context: Context, resources: Resources, albumUri: Uri): Bitmap {
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

        return albumArt!!.toBitmap()
    }
}