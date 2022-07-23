package com.example.myapplication.util

import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.domain.model.Planet

object PlanetsDiffUtil : DiffUtil.ItemCallback<Planet>() {
    override fun areItemsTheSame(oldItem: Planet, newItem: Planet): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: Planet, newItem: Planet): Boolean {
        return oldItem == newItem
    }
}
