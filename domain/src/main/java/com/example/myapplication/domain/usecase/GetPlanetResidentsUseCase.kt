package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.Person
import com.example.myapplication.domain.repository.PlanetRepository
import kotlinx.coroutines.flow.Flow

class GetPlanetResidentsUseCase(private val planetRepository: PlanetRepository) {
    suspend fun execute(urls: ArrayList<String>): Flow<ArrayList<Person>> {
        return planetRepository.getPlanetResidents(urls)
    }
}