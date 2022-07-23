package com.example.myapplication.data.repository.remote

import com.example.myapplication.data.model.FilmResponse
import com.example.myapplication.data.model.PersonResponse
import com.example.myapplication.data.model.PlanetResponse
import com.example.myapplication.data.model.PlanetRootResponse
import kotlinx.coroutines.flow.Flow

interface PlanetRemoteDataSource {
    fun getPlanetList(page: Int): Flow<PlanetRootResponse>
    fun getPlanet(id: String): Flow<PlanetResponse>
    fun getPlanetResidents(id: String): Flow<PersonResponse>
    fun getPlanetFilms(id: String): Flow<FilmResponse>
}