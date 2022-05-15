package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NameAdapter : RecyclerView.Adapter<NameAdapter.ViewHolder>() {

    var dataList = mutableListOf<Names>()
    lateinit var onClickListener: OnClickListener
    lateinit var onLongClickListener: OnLongClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.name_item, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            nameTextView.text = dataList[position].name
            nameTextView.setOnClickListener {
                onClickListener.onClick(position)
            }
            nameTextView.setOnLongClickListener {
                onLongClickListener.onLongClick(holder.adapterPosition)
                return@setOnLongClickListener true
            }
        }
    }

    override fun getItemCount(): Int = dataList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.name_text_view)
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
