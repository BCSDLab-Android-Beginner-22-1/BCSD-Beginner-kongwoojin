package com.example.myapplication.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapter.PlanetListAdapter
import com.example.myapplication.databinding.FragmentPlanetListBinding
import com.example.myapplication.view.PlanetActivity
import com.example.myapplication.viewmodel.PlanetListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlanetListFragment : Fragment() {

    private lateinit var binding: FragmentPlanetListBinding
    private val planetListViewModel: PlanetListViewModel by viewModel()
    private val planetListAdapter = PlanetListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_planet_list, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = planetListViewModel

        val dividerItemDecoration = DividerItemDecoration(
            context,
            LinearLayoutManager(context).orientation
        )

        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = planetListAdapter
            addItemDecoration(dividerItemDecoration)
        }

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (planetListViewModel.previousPage.value!! < planetListViewModel.page.value!!) {
                    if (dy > 0) {
                        binding.previousFab.hide()
                    } else if (dy < 0) {
                        binding.previousFab.show()
                    }
                }

                if (planetListViewModel.nextPage.value!! > planetListViewModel.page.value!!) {
                    if (dy > 0) {
                        binding.nextFab.hide()
                    } else if (dy < 0) {
                        binding.nextFab.show()
                    }
                }
            }
        })

        planetListAdapter.setOnClickListener {
            val intent = Intent(context, PlanetActivity::class.java)
            intent.putExtra("url", it)
            startActivity(intent)
        }

        planetListViewModel.getInitialData()

        return binding.root
    }
}