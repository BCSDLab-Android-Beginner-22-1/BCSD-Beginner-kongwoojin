package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemPlanetBinding
import com.example.myapplication.domain.model.Planet
import com.example.myapplication.util.PlanetsDiffUtil

class PlanetListAdapter : ListAdapter<Planet, PlanetListAdapter.ViewHolder>(PlanetsDiffUtil) {

    lateinit var onClickListener: OnClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPlanetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position], onClickListener)
    }

    override fun getItemCount(): Int = currentList.size

    class ViewHolder(private val binding: ItemPlanetBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(planet: Planet, onClickListener: OnClickListener) {
            binding.planet = planet

            binding.item.setOnClickListener {
                onClickListener.onClick(planet.url)
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