package com.example.myapplication.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemPlanetCompactBinding
import com.example.myapplication.domain.model.Planet
import com.example.myapplication.util.PlanetsDiffUtil
import com.example.myapplication.view.PlanetActivity

class CompactPlanetListAdapter :
    ListAdapter<Planet, CompactPlanetListAdapter.ViewHolder>(PlanetsDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemPlanetCompactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int = currentList.size

    class ViewHolder(private val binding: ItemPlanetCompactBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(planet: Planet) {
            binding.planet = planet
            binding.item.setOnClickListener {
                val context = it.context
                val intent = Intent(context, PlanetActivity::class.java)
                intent.putExtra("url", planet.url)
                context.startActivity(intent)
            }
        }
    }
}