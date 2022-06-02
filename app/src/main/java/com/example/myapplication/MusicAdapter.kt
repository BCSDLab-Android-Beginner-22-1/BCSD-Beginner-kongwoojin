package com.example.myapplication

import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import java.util.concurrent.TimeUnit

class MusicAdapter : RecyclerView.Adapter<MusicAdapter.ViewHolder>() {

    var dataList = mutableListOf<MusicData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_music, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            val position = dataList[position]
            titleTextView.text = position.title
            artistTextView.text = position.artist
            val milliseconds = position.duration
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

            val albumArt = if (position.albumArt != null) {
                BitmapDrawable(itemView.resources, position.albumArt)
            } else {
                ResourcesCompat.getDrawable(itemView.resources, R.drawable.ic_no_album_art, null)
            }
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
}
