package com.example.myapplication.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemFilmCompactBinding
import com.example.myapplication.domain.model.Film
import com.example.myapplication.util.FilmsDiffUtil
import com.example.myapplication.view.FilmActivity

class CompactFilmListAdapter :
    ListAdapter<Film, CompactFilmListAdapter.ViewHolder>(FilmsDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemFilmCompactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int = currentList.size

    class ViewHolder(private val binding: ItemFilmCompactBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(film: Film) {
            binding.film = film
            binding.item.setOnClickListener {
                val context = it.context
                val intent = Intent(context, FilmActivity::class.java)
                intent.putExtra("url", film.url)
                context.startActivity(intent)
            }
        }
    }
}