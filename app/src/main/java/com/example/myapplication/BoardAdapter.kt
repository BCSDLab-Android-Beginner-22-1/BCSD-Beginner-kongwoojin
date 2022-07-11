package com.example.myapplication

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.ViewModelSingleton.viewModel
import com.example.myapplication.databinding.ItemArticleBinding

class BoardAdapter : RecyclerView.Adapter<BoardAdapter.ViewHolder>() {

    var articleData = mutableListOf<Article>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this@BoardAdapter, articleData[position], position)
    }

    override fun getItemCount(): Int = articleData.size

    class ViewHolder(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(boardAdapter: BoardAdapter, article: Article, position: Int) {
            binding.adapter = boardAdapter
            binding.article = article
            binding.position = position

            binding.item.setOnClickListener {
                val intent = Intent(binding.root.context, WriteActivity::class.java)
                intent.putExtra("position", position)
                binding.root.context.startActivity(intent)
            }

            binding.item.setOnLongClickListener {
                viewModel.removeData(position)
                true
            }
        }
    }
}
