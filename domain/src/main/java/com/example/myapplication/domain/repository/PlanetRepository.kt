package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.Film
import com.example.myapplication.domain.model.Person
import com.example.myapplication.domain.model.Planet
import com.example.myapplication.domain.model.PlanetRoot
import kotlinx.coroutines.flow.Flow

interface PlanetRepository {
    suspend fun getPlanetList(page: Int): Flow<PlanetRoot>
    suspend fun getPlanet(url: String): Flow<Planet>
    suspend fun getPlanetResidents(urls: ArrayList<String>): Flow<ArrayList<Person>>
    suspend fun getPlanetFilms(urls: ArrayList<String>): Flow<ArrayList<Film>>
}