package com.example.myapplication.view

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapter.CompactPersonListAdapter
import com.example.myapplication.adapter.CompactPlanetListAdapter
import com.example.myapplication.databinding.ActivityFilmBinding
import com.example.myapplication.viewmodel.FilmViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class FilmActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFilmBinding
    private val filmViewModel: FilmViewModel by viewModel()
    private val compactPersonListAdapter = CompactPersonListAdapter()
    private val compactPlanetListAdapter = CompactPlanetListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_film)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        filmViewModel.setUrl(intent.getStringExtra("url")!!)

        val dividerItemDecoration = DividerItemDecoration(
            this,
            LinearLayoutManager(this).orientation
        )

        binding.charactersRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = compactPersonListAdapter
            addItemDecoration(dividerItemDecoration)
        }

        binding.planetsRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = compactPlanetListAdapter
            addItemDecoration(dividerItemDecoration)
        }

        binding.lifecycleOwner = this
        binding.vm = filmViewModel

        filmViewModel.getData()
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