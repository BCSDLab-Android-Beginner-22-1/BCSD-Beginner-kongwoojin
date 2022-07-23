package com.example.myapplication.data.repository.remote

import com.example.myapplication.data.model.FilmResponse
import com.example.myapplication.data.model.FilmRootResponse
import com.example.myapplication.data.model.PersonResponse
import com.example.myapplication.data.model.PlanetResponse
import kotlinx.coroutines.flow.Flow

interface FilmRemoteDataSource {
    fun getFilmList(page: Int): Flow<FilmRootResponse>
    fun getFilm(id: String): Flow<FilmResponse>
    fun getFilmCharacters(id: String): Flow<PersonResponse>
    fun getFilmPlanets(id: String): Flow<PlanetResponse>
}