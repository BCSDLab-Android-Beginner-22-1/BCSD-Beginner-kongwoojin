package com.example.myapplication.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemPersonCompactBinding
import com.example.myapplication.domain.model.Person
import com.example.myapplication.util.PeopleDiffUtil
import com.example.myapplication.view.PersonActivity

class CompactPersonListAdapter :
    ListAdapter<Person, CompactPersonListAdapter.ViewHolder>(PeopleDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemPersonCompactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int = currentList.size

    class ViewHolder(private val binding: ItemPersonCompactBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(person: Person) {
            binding.person = person
            binding.item.setOnClickListener {
                val context = it.context
                val intent = Intent(context, PersonActivity::class.java)
                intent.putExtra("url", person.url)
                context.startActivity(intent)
            }
        }
    }
}