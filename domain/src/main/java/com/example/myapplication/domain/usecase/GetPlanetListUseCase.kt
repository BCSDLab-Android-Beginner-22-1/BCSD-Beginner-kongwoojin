package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.PlanetRoot
import com.example.myapplication.domain.repository.PlanetRepository
import kotlinx.coroutines.flow.Flow

class GetPlanetListUseCase(private val planetRepository: PlanetRepository) {
    suspend fun execute(page: Int): Flow<PlanetRoot> {
        return planetRepository.getPlanetList(page)
    }
}