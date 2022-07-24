package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.Planet
import com.example.myapplication.domain.repository.PlanetRepository
import kotlinx.coroutines.flow.Flow

class GetPlanetUseCase(private val planetRepository: PlanetRepository) {
    suspend fun execute(url: String): Flow<Planet> {
        return planetRepository.getPlanet(url)
    }
}