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
import com.example.myapplication.adapter.FilmListAdapter
import com.example.myapplication.databinding.FragmentFilmListBinding
import com.example.myapplication.view.FilmActivity
import com.example.myapplication.viewmodel.FilmListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilmListFragment : Fragment() {

    private lateinit var binding: FragmentFilmListBinding
    private val filmListViewModel: FilmListViewModel by viewModel()
    private val filmListAdapter = FilmListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_film_list, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = filmListViewModel

        val dividerItemDecoration = DividerItemDecoration(
            context,
            LinearLayoutManager(context).orientation
        )

        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = filmListAdapter
            addItemDecoration(dividerItemDecoration)
        }

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (filmListViewModel.previousPage.value!! < filmListViewModel.page.value!!) {
                    if (dy > 0) {
                        binding.previousFab.hide()
                    } else if (dy < 0) {
                        binding.previousFab.show()
                    }
                }

                if (filmListViewModel.nextPage.value!! > filmListViewModel.page.value!!) {
                    if (dy > 0) {
                        binding.nextFab.hide()
                    } else if (dy < 0) {
                        binding.nextFab.show()
                    }
                }
            }
        })

        filmListAdapter.setOnClickListener {
            val intent = Intent(context, FilmActivity::class.java)
            intent.putExtra("url", it)
            startActivity(intent)
        }

        filmListViewModel.getInitialData()

        return binding.root
    }
}