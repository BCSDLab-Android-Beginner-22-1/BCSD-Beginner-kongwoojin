package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.Person
import com.example.myapplication.domain.repository.PersonRepository
import kotlinx.coroutines.flow.Flow

class GetPersonUseCase(private val personRepository: PersonRepository) {
    suspend fun execute(url: String): Flow<Person> {
        return personRepository.getPerson(url)
    }
}