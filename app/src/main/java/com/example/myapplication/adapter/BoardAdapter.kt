package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemArticleBinding
import com.example.myapplication.domain.model.Article
import com.example.myapplication.util.BoardDiffUtil

class BoardAdapter : ListAdapter<Article, BoardAdapter.ViewHolder>(BoardDiffUtil) {

    lateinit var onClickListener: OnClickListener
    lateinit var onLongClickListener: OnLongClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position], onClickListener, onLongClickListener)
    }

    override fun getItemCount(): Int = currentList.size

    class ViewHolder(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            article: Article,
            onClickListener: OnClickListener,
            onLongClickListener: OnLongClickListener
        ) {
            binding.article = article

            binding.item.setOnClickListener {
                onClickListener.onClick(adapterPosition)
            }

            binding.item.setOnLongClickListener {
                onLongClickListener.onLongClick(adapterPosition)
                true
            }
        }
    }

    interface OnClickListener {
        fun onClick(position: Int)
    }

    interface OnLongClickListener {
        fun onLongClick(position: Int)
    }

    inline fun setOnClickListener(crossinline item: (Int) -> Unit) {
        this.onClickListener = object : OnClickListener {
            override fun onClick(position: Int) {
                item(position)
            }
        }
    }

    inline fun setOnLongClickListener(crossinline item: (Int) -> Unit) {
        this.onLongClickListener = object : OnLongClickListener {
            override fun onLongClick(position: Int) {
                item(position)
            }
        }
    }
}
