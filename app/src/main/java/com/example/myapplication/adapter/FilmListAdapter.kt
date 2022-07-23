package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemFilmBinding
import com.example.myapplication.domain.model.Film
import com.example.myapplication.util.FilmsDiffUtil

class FilmListAdapter : ListAdapter<Film, FilmListAdapter.ViewHolder>(FilmsDiffUtil) {

    lateinit var onClickListener: OnClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position], onClickListener)
    }

    override fun getItemCount(): Int = currentList.size

    class ViewHolder(private val binding: ItemFilmBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(film: Film, onClickListener: OnClickListener) {
            binding.film = film

            binding.item.setOnClickListener {
                onClickListener.onClick(film.url)
            }
        }
    }

    interface OnClickListener {
        fun onClick(url: String)
    }

    inline fun setOnClickListener(crossinline item: (String) -> Unit) {
        this.onClickListener = object : OnClickListener {
            override fun onClick(url: String) {
                item(url)
            }
        }
    }
}