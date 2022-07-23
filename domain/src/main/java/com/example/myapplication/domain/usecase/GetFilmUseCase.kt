package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.Film
import com.example.myapplication.domain.repository.FilmRepository
import kotlinx.coroutines.flow.Flow

class GetFilmUseCase(private val filmRepository: FilmRepository) {
    suspend fun execute(url: String): Flow<Film> {
        return filmRepository.getFilm(url)
    }
}