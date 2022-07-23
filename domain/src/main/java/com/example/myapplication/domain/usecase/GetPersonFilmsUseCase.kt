package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.Film
import com.example.myapplication.domain.repository.PersonRepository
import kotlinx.coroutines.flow.Flow

class GetPersonFilmsUseCase(private val personRepository: PersonRepository) {
    suspend fun execute(urls: ArrayList<String>): Flow<ArrayList<Film>> {
        return personRepository.getPersonFilms(urls)
    }
}