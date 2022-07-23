package com.example.myapplication.data.repository.remote

import com.example.myapplication.data.api.RetrofitBuilder
import com.example.myapplication.data.model.FilmResponse
import com.example.myapplication.data.model.PersonResponse
import com.example.myapplication.data.model.PlanetResponse
import com.example.myapplication.data.model.PlanetRootResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlanetRemoteDataSourceImpl : PlanetRemoteDataSource {
    override fun getPlanetList(page: Int): Flow<PlanetRootResponse> {
        return flow {
            emit(RetrofitBuilder.api.getPlanetList(page))
        }
    }

    override fun getPlanet(id: String): Flow<PlanetResponse> {
        return flow {
            emit(RetrofitBuilder.api.getPlanet(id))
        }
    }

    override fun getPlanetResidents(id: String): Flow<PersonResponse> {
        return flow {
            emit(RetrofitBuilder.api.getPerson(id))
        }
    }

    override fun getPlanetFilms(id: String): Flow<FilmResponse> {
        return flow {
            emit(RetrofitBuilder.api.getFilm(id))
        }
    }
}