package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.Film
import com.example.myapplication.domain.repository.PlanetRepository
import kotlinx.coroutines.flow.Flow

class GetPlanetFilmsUseCase(private val planetRepository: PlanetRepository) {
    suspend fun execute(urls: ArrayList<String>): Flow<ArrayList<Film>> {
        return planetRepository.getPlanetFilms(urls)
    }
}