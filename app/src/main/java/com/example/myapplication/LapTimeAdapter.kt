package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.concurrent.TimeUnit

class LapTimeAdapter : RecyclerView.Adapter<LapTimeAdapter.ViewHolder>() {

    var lapList = mutableListOf<LapTime>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_lap_time, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            lapNumberTextView.text = lapList[position].number.toString()
            lapTimeTextView.text = convertTime(lapList[position].lap)
            if (position + 2 > itemCount) {
                intervalTextView.text = convertTime(lapList[position].lap)
            } else {
                intervalTextView.text =
                    convertTime(lapList[position].lap - lapList[position + 1].lap)
            }
        }
    }

    override fun getItemCount(): Int = lapList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val lapNumberTextView: TextView = itemView.findViewById(R.id.lap_number_text_view)
        val lapTimeTextView: TextView = itemView.findViewById(R.id.lap_time_text_view)
        val intervalTextView: TextView = itemView.findViewById(R.id.interval_time_text_view)
    }

    private fun convertTime(time: Long): String {
        val nowMinutes = TimeUnit.MILLISECONDS.toMinutes(time)
        val nowSeconds =
            TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(nowMinutes)
        val nowMillis =
            time - TimeUnit.SECONDS.toMillis(nowSeconds) - TimeUnit.MINUTES.toMillis(nowMinutes)

        return String.format("%02d:%02d.%02d", nowMinutes, nowSeconds, nowMillis / 10)
    }
}
