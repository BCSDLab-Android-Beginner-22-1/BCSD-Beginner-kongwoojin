package com.example.myapplication.view

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapter.CompactFilmListAdapter
import com.example.myapplication.adapter.CompactPersonListAdapter
import com.example.myapplication.databinding.ActivityPlanetBinding
import com.example.myapplication.viewmodel.PlanetViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlanetActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlanetBinding
    private val planetViewModel: PlanetViewModel by viewModel()
    private val compactPersonListAdapter = CompactPersonListAdapter()
    private val compactFilmListAdapter = CompactFilmListAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_planet)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        planetViewModel.setUrl(intent.getStringExtra("url")!!)

        val dividerItemDecoration = DividerItemDecoration(
            this,
            LinearLayoutManager(this).orientation
        )

        binding.residentsRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = compactPersonListAdapter
            addItemDecoration(dividerItemDecoration)
        }

        binding.filmsRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = compactFilmListAdapter
            addItemDecoration(dividerItemDecoration)
        }

        binding.lifecycleOwner = this
        binding.vm = planetViewModel

        planetViewModel.getData()
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