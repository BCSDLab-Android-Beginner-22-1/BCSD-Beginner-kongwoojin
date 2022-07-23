package com.example.myapplication.view

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapter.CompactFilmListAdapter
import com.example.myapplication.databinding.ActivityPersonBinding
import com.example.myapplication.viewmodel.PersonViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PersonActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPersonBinding
    private val personViewModel: PersonViewModel by viewModel()
    private val compactFilmListAdapter = CompactFilmListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_person)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        personViewModel.setUrl(intent.getStringExtra("url")!!)

        val dividerItemDecoration = DividerItemDecoration(
            this,
            LinearLayoutManager(this).orientation
        )

        binding.filmsRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = compactFilmListAdapter
            addItemDecoration(dividerItemDecoration)
        }

        binding.lifecycleOwner = this
        binding.vm = personViewModel

        personViewModel.getData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}