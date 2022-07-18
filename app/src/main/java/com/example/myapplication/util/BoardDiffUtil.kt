package com.example.myapplication.util

import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.domain.model.Article

object BoardDiffUtil : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}
