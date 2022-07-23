package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.Film
import com.example.myapplication.domain.model.Person
import com.example.myapplication.domain.model.PersonRoot
import com.example.myapplication.domain.model.Planet
import kotlinx.coroutines.flow.Flow

interface PersonRepository {
    suspend fun getPersonList(page: Int): Flow<PersonRoot>
    suspend fun getPerson(url: String): Flow<Person>
    suspend fun getHomeWorld(url: String): Flow<Planet>
    suspend fun getPersonFilms(urls: ArrayList<String>): Flow<ArrayList<Film>>
}