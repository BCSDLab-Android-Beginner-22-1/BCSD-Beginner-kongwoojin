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
import com.example.myapplication.adapter.PersonListAdapter
import com.example.myapplication.databinding.FragmentPersonListBinding
import com.example.myapplication.view.PersonActivity
import com.example.myapplication.viewmodel.PersonListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PersonListFragment : Fragment() {

    private lateinit var binding: FragmentPersonListBinding
    private val personListViewModel: PersonListViewModel by viewModel()
    private val personListAdapter = PersonListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_person_list, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = personListViewModel

        val dividerItemDecoration = DividerItemDecoration(
            context,
            LinearLayoutManager(context).orientation
        )

        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = personListAdapter
            addItemDecoration(dividerItemDecoration)
        }

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (personListViewModel.previousPage.value!! < personListViewModel.page.value!!) {
                    if (dy > 0) {
                        binding.previousFab.hide()
                    } else if (dy < 0) {
                        binding.previousFab.show()
                    }
                }

                if (personListViewModel.nextPage.value!! > personListViewModel.page.value!!) {
                    if (dy > 0) {
                        binding.nextFab.hide()
                    } else if (dy < 0) {
                        binding.nextFab.show()
                    }
                }
            }
        })

        personListAdapter.setOnClickListener {
            val intent = Intent(context, PersonActivity::class.java)
            intent.putExtra("url", it)
            startActivity(intent)
        }

        personListViewModel.getInitialData()

        return binding.root
    }
}