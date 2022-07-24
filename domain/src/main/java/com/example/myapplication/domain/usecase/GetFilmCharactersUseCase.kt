package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.Person
import com.example.myapplication.domain.repository.FilmRepository
import kotlinx.coroutines.flow.Flow

class GetFilmCharactersUseCase(private val filmRepository: FilmRepository) {
    suspend fun execute(urls: ArrayList<String>): Flow<ArrayList<Person>> {
        return filmRepository.getFilmCharacters(urls)
    }
}