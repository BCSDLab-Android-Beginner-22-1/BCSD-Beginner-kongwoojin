package com.example.myapplication.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapter.*
import com.example.myapplication.domain.model.Film
import com.example.myapplication.domain.model.Person
import com.example.myapplication.domain.model.Planet

object BindingAdapter {
    @BindingAdapter("films_items")
    @JvmStatic
    fun setFilmsItems(recyclerView: RecyclerView, items: ArrayList<Film>?) {
        if (recyclerView.adapter == null)
            recyclerView.adapter = FilmListAdapter()

        val filmListAdapter = recyclerView.adapter as FilmListAdapter

        filmListAdapter.submitList(items)
    }

    @BindingAdapter("people_items")
    @JvmStatic
    fun sePeopleItems(recyclerView: RecyclerView, items: ArrayList<Person>?) {
        if (recyclerView.adapter == null)
            recyclerView.adapter = PersonListAdapter()

        val personListAdapter = recyclerView.adapter as PersonListAdapter

        personListAdapter.submitList(items)
    }

    @BindingAdapter("planets_items")
    @JvmStatic
    fun setPlanetsItems(recyclerView: RecyclerView, items: ArrayList<Planet>?) {
        if (recyclerView.adapter == null)
            recyclerView.adapter = PlanetListAdapter()

        val planetListAdapter = recyclerView.adapter as PlanetListAdapter

        planetListAdapter.submitList(items)
    }

    @BindingAdapter("films_compact_items")
    @JvmStatic
    fun seFilmsCompactItems(recyclerView: RecyclerView, items: ArrayList<Film>?) {
        if (recyclerView.adapter == null)
            recyclerView.adapter = CompactFilmListAdapter()

        val compactFilmListAdapter = recyclerView.adapter as CompactFilmListAdapter

        compactFilmListAdapter.submitList(items)
    }

    @BindingAdapter("people_compact_items")
    @JvmStatic
    fun sePeopleCompactItems(recyclerView: RecyclerView, items: ArrayList<Person>?) {
        if (recyclerView.adapter == null)
            recyclerView.adapter = CompactPersonListAdapter()

        val compactPersonListAdapter = recyclerView.adapter as CompactPersonListAdapter

        compactPersonListAdapter.submitList(items)
    }

    @BindingAdapter("planets_compact_items")
    @JvmStatic
    fun sePlanetsCompactItems(recyclerView: RecyclerView, items: ArrayList<Planet>?) {
        if (recyclerView.adapter == null)
            recyclerView.adapter = CompactPlanetListAdapter()

        val compactPlanetListAdapter = recyclerView.adapter as CompactPlanetListAdapter

        compactPlanetListAdapter.submitList(items)
    }
}
