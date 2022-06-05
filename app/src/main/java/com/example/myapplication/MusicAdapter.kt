package com.example.myapplication

import android.content.Context
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import java.io.FileNotFoundException
import java.io.InputStream
import java.util.concurrent.TimeUnit

class MusicAdapter : RecyclerView.Adapter<MusicAdapter.ViewHolder>() {

    var dataList = mutableListOf<MusicData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_music, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            val item = dataList[position]
            titleTextView.text = item.title
            artistTextView.text = item.artist
            val milliseconds = item.duration
            val hours = TimeUnit.MILLISECONDS.toHours(milliseconds)
            val minutes =
                TimeUnit.MILLISECONDS.toMinutes(milliseconds) - TimeUnit.HOURS.toMinutes(hours)
            val seconds =
                TimeUnit.MILLISECONDS.toSeconds(milliseconds) - TimeUnit.MINUTES.toSeconds(minutes)

            val duration = if (hours.toInt() != 0) {
                String.format("%02d:%02d:%02d", hours, minutes, seconds)
            } else {
                String.format("%02d:%02d", minutes, seconds)
            }
            durationTextView.text = duration

            val albumArt = getAlbumArt(itemView.context, itemView.resources, item.albumUri)
            albumArtImage.setImageDrawable(albumArt)
        }
    }

    override fun getItemCount(): Int = dataList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.title_text_view)
        val artistTextView: TextView = itemView.findViewById(R.id.artist_text_view)
        val durationTextView: TextView = itemView.findViewById(R.id.duration_text_view)
        val albumArtImage: ImageView = itemView.findViewById(R.id.album_art_image)
    }

    private fun getAlbumArt(context: Context, resources: Resources, albumUri: Uri): Drawable? {
        var inputStream: InputStream? = null
        val albumArt: Drawable? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            try {
                BitmapDrawable(
                    resources,
                    context.contentResolver.loadThumbnail(albumUri, Size(100, 100), null)
                )
            } catch (e: FileNotFoundException) {
                ResourcesCompat.getDrawable(resources, R.drawable.ic_no_album_art, null)
            }
        } else {
            try {
                inputStream = context.contentResolver.openInputStream(albumUri)
                val option = BitmapFactory.Options()
                option.outWidth = 100
                option.outHeight = 100
                option.inSampleSize = 2
                BitmapDrawable(resources, BitmapFactory.decodeStream(inputStream, null, option))
            } catch (e: FileNotFoundException) {
                ResourcesCompat.getDrawable(resources, R.drawable.ic_no_album_art, null)
            }
        }
        inputStream?.close()

        return albumArt
    }
}
