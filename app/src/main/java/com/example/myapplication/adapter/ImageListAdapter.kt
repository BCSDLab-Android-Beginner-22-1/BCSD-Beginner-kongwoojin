package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.domain.model.Images
import com.example.myapplication.databinding.ItemImageBinding

class ImageListAdapter : RecyclerView.Adapter<ImageListAdapter.ViewHolder>() {

    var imageData = mutableListOf<Images>()

    lateinit var onClickListener: OnClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imageData[position], position, onClickListener)
    }

    override fun getItemCount(): Int = imageData.size

    class ViewHolder(private val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            images: Images,
            position: Int,
            onClickListener: OnClickListener
        ) {
            binding.imageData = images

            binding.item.setOnClickListener {
                onClickListener.onClick(position)
            }
        }
    }

    interface OnClickListener {
        fun onClick(position: Int)
    }

    inline fun setOnClickListener(crossinline item: (Int) -> Unit) {
        this.onClickListener = object : OnClickListener {
            override fun onClick(position: Int) {
                item(position)
            }
        }
    }
}
