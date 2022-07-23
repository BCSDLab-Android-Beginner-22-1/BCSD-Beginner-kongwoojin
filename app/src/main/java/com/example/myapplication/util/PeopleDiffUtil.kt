package com.example.myapplication.util

import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.domain.model.Person

object PeopleDiffUtil : DiffUtil.ItemCallback<Person>() {
    override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
        return oldItem == newItem
    }
}
