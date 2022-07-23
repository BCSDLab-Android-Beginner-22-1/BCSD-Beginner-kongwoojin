package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemPersonBinding
import com.example.myapplication.domain.model.Person
import com.example.myapplication.util.PeopleDiffUtil

class PersonListAdapter : ListAdapter<Person, PersonListAdapter.ViewHolder>(PeopleDiffUtil) {

    lateinit var onClickListener: OnClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPersonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position], onClickListener)
    }

    override fun getItemCount(): Int = currentList.size

    class ViewHolder(private val binding: ItemPersonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(person: Person, onClickListener: OnClickListener) {
            binding.person = person

            binding.item.setOnClickListener {
                onClickListener.onClick(person.url)
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