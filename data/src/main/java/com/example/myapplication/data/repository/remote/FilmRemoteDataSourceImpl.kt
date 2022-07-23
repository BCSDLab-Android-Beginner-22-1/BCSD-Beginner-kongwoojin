package com.example.myapplication.data.repository.remote

import com.example.myapplication.data.api.RetrofitBuilder
import com.example.myapplication.data.model.FilmResponse
import com.example.myapplication.data.model.FilmRootResponse
import com.example.myapplication.data.model.PersonResponse
import com.example.myapplication.data.model.PlanetResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FilmRemoteDataSourceImpl : FilmRemoteDataSource {
    override fun getFilmList(page: Int): Flow<FilmRootResponse> {
        return flow {
            emit(RetrofitBuilder.api.getFilmList(page))
        }
    }

    override fun getFilm(id: String): Flow<FilmResponse> {
        return flow {
            emit(RetrofitBuilder.api.getFilm(id))
        }
    }

    override fun getFilmCharacters(id: String): Flow<PersonResponse> {
        return flow {
            emit(RetrofitBuilder.api.getPerson(id))
        }
    }

    override fun getFilmPlanets(id: String): Flow<PlanetResponse> {
        return flow {
            emit(RetrofitBuilder.api.getPlanet(id))
        }
    }
}