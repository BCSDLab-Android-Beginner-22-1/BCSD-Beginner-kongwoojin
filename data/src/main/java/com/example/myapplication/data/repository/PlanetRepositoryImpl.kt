package com.example.myapplication.data.repository

import com.example.myapplication.data.mapper.FilmMapper
import com.example.myapplication.data.mapper.PersonMapper
import com.example.myapplication.data.mapper.PlanetMapper
import com.example.myapplication.data.repository.remote.PlanetRemoteDataSource
import com.example.myapplication.domain.model.Film
import com.example.myapplication.domain.model.Person
import com.example.myapplication.domain.model.Planet
import com.example.myapplication.domain.model.PlanetRoot
import com.example.myapplication.domain.repository.PlanetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry

class PlanetRepositoryImpl(private val planetRemoteDataSource: PlanetRemoteDataSource) :
    PlanetRepository {
    override suspend fun getPlanetList(page: Int): Flow<PlanetRoot> {
        return flow {
            planetRemoteDataSource.getPlanetList(page)
                .retry(3)
                .catch {
                    print(it.toString())
                }
                .collect {
                    emit(PlanetMapper.mapToPlanetList(it))
                }
        }
    }

    override suspend fun getPlanet(url: String): Flow<Planet> {
        val urlSplit = url.split("/")
        val id = urlSplit[urlSplit.lastIndex - 1]

        return flow {
            planetRemoteDataSource.getPlanet(id)
                .retry(3)
                .catch {
                    print(it.toString())
                }
                .collect {
                    emit(PlanetMapper.mapToPlanet(it))
                }
        }
    }

    override suspend fun getPlanetResidents(urls: ArrayList<String>): Flow<ArrayList<Person>> {
        val residents = ArrayList<Person>()

        for (url in urls) {
            val urlSplit = url.split("/")
            val id = urlSplit[urlSplit.lastIndex - 1]

            planetRemoteDataSource.getPlanetResidents(id)
                .retry(3)
                .catch {
                    print(it.toString())
                }
                .collect {
                    residents.add(PersonMapper.mapToPerson(it))
                }
        }
        return flow {
            emit(residents)
        }
    }

    override suspend fun getPlanetFilms(urls: ArrayList<String>): Flow<ArrayList<Film>> {
        val films = ArrayList<Film>()

        for (url in urls) {
            val urlSplit = url.split("/")
            val id = urlSplit[urlSplit.lastIndex - 1]

            planetRemoteDataSource.getPlanetFilms(id)
                .retry(3)
                .catch {
                    print(it.toString())
                }
                .collect {
                    films.add(FilmMapper.mapToFilms(it))
                }
        }
        return flow {
            emit(films)
        }
    }
}