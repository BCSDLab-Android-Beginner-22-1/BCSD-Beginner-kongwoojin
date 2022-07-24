package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.Film
import com.example.myapplication.domain.model.FilmRoot
import com.example.myapplication.domain.repository.FilmRepository
import kotlinx.coroutines.flow.Flow

class GetFilmListUseCase(private val filmRepository: FilmRepository) {
    suspend fun execute(page: Int): Flow<FilmRoot> {
        return filmRepository.getFilmList(page)
    }
}