package com.example.myapplication.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.example.myapplication.adapter.BoardAdapter
import com.example.myapplication.adapter.ImageListAdapter
import com.example.myapplication.domain.model.Article
import com.example.myapplication.domain.model.Images

object BindingAdapter {
    @BindingAdapter("article_items")
    @JvmStatic
    fun setArticleItems(recyclerView: RecyclerView, items: ArrayList<Article>) {
        if (recyclerView.adapter == null)
            recyclerView.adapter = BoardAdapter()

        val boardAdapter = recyclerView.adapter as BoardAdapter

        boardAdapter.submitList(items)
    }

    @BindingAdapter("image_items")
    @JvmStatic
    fun setImageItems(recyclerView: RecyclerView, items: ArrayList<Images>) {
        if (recyclerView.adapter == null)
            recyclerView.adapter = ImageListAdapter()

        val imageListAdapter = recyclerView.adapter as ImageListAdapter

        imageListAdapter.imageData = items
        imageListAdapter.notifyDataSetChanged()
    }

    @BindingAdapter("image")
    @JvmStatic
    fun bindImageFromUrl(imageview: ImageView, imageUrl: String?) {
        val requestBuilder: RequestBuilder<Drawable> = Glide.with(imageview.context)
            .asDrawable().sizeMultiplier(0.1f)

        if (!imageUrl.isNullOrEmpty()) {
            Glide.with(imageview.context)
                .load(imageUrl)
                .thumbnail(requestBuilder)
                .into(imageview)
        } else {
            imageview.setImageDrawable(null)
        }
    }
}
