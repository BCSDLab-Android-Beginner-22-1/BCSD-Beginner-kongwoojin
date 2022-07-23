package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.Planet
import com.example.myapplication.domain.repository.FilmRepository
import kotlinx.coroutines.flow.Flow

class GetFilmPlanetsUseCase(private val filmRepository: FilmRepository) {
    suspend fun execute(urls: ArrayList<String>): Flow<ArrayList<Planet>> {
        return filmRepository.getFilmPlanets(urls)
    }
}