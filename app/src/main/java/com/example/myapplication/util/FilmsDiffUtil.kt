package com.example.myapplication.util

import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.domain.model.Film

object FilmsDiffUtil : DiffUtil.ItemCallback<Film>() {
    override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean {
        return oldItem == newItem
    }
}
