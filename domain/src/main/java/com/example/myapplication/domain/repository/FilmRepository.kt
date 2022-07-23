package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.Film
import com.example.myapplication.domain.model.FilmRoot
import com.example.myapplication.domain.model.Person
import com.example.myapplication.domain.model.Planet
import kotlinx.coroutines.flow.Flow

interface FilmRepository {
    suspend fun getFilmList(page: Int): Flow<FilmRoot>
    suspend fun getFilm(url: String): Flow<Film>
    suspend fun getFilmCharacters(urls: ArrayList<String>): Flow<ArrayList<Person>>
    suspend fun getFilmPlanets(urls: ArrayList<String>): Flow<ArrayList<Planet>>
}