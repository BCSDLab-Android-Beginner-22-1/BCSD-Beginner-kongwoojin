package com.example.myapplication.data.repository.remote

import com.example.myapplication.data.api.RetrofitBuilder
import com.example.myapplication.data.model.FilmResponse
import com.example.myapplication.data.model.PersonResponse
import com.example.myapplication.data.model.PersonRootResponse
import com.example.myapplication.data.model.PlanetResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PersonRemoteDataSourceImpl : PersonRemoteDataSource {
    override fun getPersonList(page: Int): Flow<PersonRootResponse> {
        return flow {
            emit(RetrofitBuilder.api.getPersonList(page))
        }
    }

    override fun getPerson(id: String): Flow<PersonResponse> {
        return flow {
            emit(RetrofitBuilder.api.getPerson(id))
        }
    }

    override fun getPersonHomeWorld(id: String): Flow<PlanetResponse> {
        return flow {
            emit(RetrofitBuilder.api.getPlanet(id))
        }
    }

    override fun getPersonFilms(id: String): Flow<FilmResponse> {
        return flow {
            emit(RetrofitBuilder.api.getFilm(id))
        }
    }
}