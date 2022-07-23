package com.example.myapplication.data.repository

import com.example.myapplication.data.mapper.FilmMapper
import com.example.myapplication.data.mapper.PersonMapper
import com.example.myapplication.data.mapper.PlanetMapper
import com.example.myapplication.data.repository.remote.PersonRemoteDataSource
import com.example.myapplication.domain.model.Film
import com.example.myapplication.domain.model.Person
import com.example.myapplication.domain.model.PersonRoot
import com.example.myapplication.domain.model.Planet
import com.example.myapplication.domain.repository.PersonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry

class PersonRepositoryImpl(private val personRemoteDataSource: PersonRemoteDataSource) :
    PersonRepository {
    override suspend fun getPersonList(page: Int): Flow<PersonRoot> {
        return flow {
            personRemoteDataSource.getPersonList(page)
                .retry(3)
                .catch {
                    print(it.toString())
                }
                .collect {
                    emit(PersonMapper.mapToPersonList(it))
                }
        }
    }

    override suspend fun getPerson(url: String): Flow<Person> {
        val urlSplit = url.split("/")
        val id = urlSplit[urlSplit.lastIndex - 1]

        return flow {
            personRemoteDataSource.getPerson(id)
                .retry(3)
                .catch {
                    print(it.toString())
                }
                .collect {
                    emit(PersonMapper.mapToPerson(it))
                }
        }
    }

    override suspend fun getHomeWorld(url: String): Flow<Planet> {
        val urlSplit = url.split("/")
        val id = urlSplit[urlSplit.lastIndex - 1]

        return flow {
            personRemoteDataSource.getPersonHomeWorld(id)
                .retry(3)
                .catch {
                    print(it.toString())
                }
                .collect {
                    emit(PlanetMapper.mapToPlanet(it))
                }
        }
    }

    override suspend fun getPersonFilms(urls: ArrayList<String>): Flow<ArrayList<Film>> {
        val films = ArrayList<Film>()

        for (url in urls) {
            val urlSplit = url.split("/")
            val id = urlSplit[urlSplit.lastIndex - 1]

            personRemoteDataSource.getPersonFilms(id)
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