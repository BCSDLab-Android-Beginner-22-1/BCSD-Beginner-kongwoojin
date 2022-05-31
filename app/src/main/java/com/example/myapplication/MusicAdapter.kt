package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
            val duration = String.format("%02d:%02d:%02d", hours, minutes, seconds)
            durationTextView.text = duration
        }
    }

    override fun getItemCount(): Int = dataList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.title_text_view)
        val artistTextView: TextView = itemView.findViewById(R.id.artist_text_view)
        val durationTextView: TextView = itemView.findViewById(R.id.duration_text_view)
    }
}
