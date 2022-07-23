package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.Planet
import com.example.myapplication.domain.repository.PersonRepository
import kotlinx.coroutines.flow.Flow

class GetPersonHomeWorldUseCase(private val personRepository: PersonRepository) {
    suspend fun execute(url: String): Flow<Planet> {
        return personRepository.getHomeWorld(url)
    }
}