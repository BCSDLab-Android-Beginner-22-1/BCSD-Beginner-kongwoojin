package com.example.myapplication.data.repository.remote

import com.example.myapplication.data.model.FilmResponse
import com.example.myapplication.data.model.PersonResponse
import com.example.myapplication.data.model.PersonRootResponse
import com.example.myapplication.data.model.PlanetResponse
import kotlinx.coroutines.flow.Flow

interface PersonRemoteDataSource {
    fun getPersonList(page: Int): Flow<PersonRootResponse>
    fun getPerson(id: String): Flow<PersonResponse>
    fun getPersonHomeWorld(id: String): Flow<PlanetResponse>
    fun getPersonFilms(id: String): Flow<FilmResponse>
}