package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.PersonRoot
import com.example.myapplication.domain.repository.PersonRepository
import kotlinx.coroutines.flow.Flow

class GetPersonListUseCase(private val personRepository: PersonRepository) {
    suspend fun execute(page: Int): Flow<PersonRoot> {
        return personRepository.getPersonList(page)
    }
}