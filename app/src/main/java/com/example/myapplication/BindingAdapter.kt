package com.example.myapplication

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

object BindingAdapter {

    @BindingAdapter("items")
    @JvmStatic
    fun setItems(recyclerView: RecyclerView, items: ArrayList<Article>) {

        if (recyclerView.adapter == null)
            recyclerView.adapter = BoardAdapter()

        val boardAdapter = recyclerView.adapter as BoardAdapter

        boardAdapter.articleData = items
        boardAdapter.notifyDataSetChanged()
    }
}
