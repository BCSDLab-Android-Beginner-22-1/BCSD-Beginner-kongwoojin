package com.example.myapplication.data.repository

import com.example.myapplication.data.mapper.FilmMapper
import com.example.myapplication.data.mapper.PersonMapper
import com.example.myapplication.data.mapper.PlanetMapper
import com.example.myapplication.data.repository.remote.FilmRemoteDataSource
import com.example.myapplication.domain.model.Film
import com.example.myapplication.domain.model.FilmRoot
import com.example.myapplication.domain.model.Person
import com.example.myapplication.domain.model.Planet
import com.example.myapplication.domain.repository.FilmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry

class FilmRepositoryImpl(private val filmRemoteDataSource: FilmRemoteDataSource) : FilmRepository {
    override suspend fun getFilmList(page: Int): Flow<FilmRoot> {
        return flow {
            filmRemoteDataSource.getFilmList(page)
                .retry(3)
                .catch {
                    print(it.toString())
                }
                .collect {
                    emit(FilmMapper.mapToFilmList(it))
                }
        }
    }

    override suspend fun getFilm(url: String): Flow<Film> {
        val urlSplit = url.split("/")
        val id = urlSplit[urlSplit.lastIndex - 1]

        return flow {
            filmRemoteDataSource.getFilm(id)
                .retry(3)
                .catch {
                    print(it.toString())
                }
                .collect {
                    emit(FilmMapper.mapToFilms(it))
                }
        }
    }

    override suspend fun getFilmCharacters(urls: ArrayList<String>): Flow<ArrayList<Person>> {
        val characters = ArrayList<Person>()

        for (url in urls) {
            val urlSplit = url.split("/")
            val id = urlSplit[urlSplit.lastIndex - 1]

            filmRemoteDataSource.getFilmCharacters(id)
                .retry(3)
                .catch {
                    print(it.toString())
                }
                .collect {
                    characters.add(PersonMapper.mapToPerson(it))
                }
        }
        return flow {
            emit(characters)
        }
    }

    override suspend fun getFilmPlanets(urls: ArrayList<String>): Flow<ArrayList<Planet>> {
        val planets = ArrayList<Planet>()

        for (url in urls) {
            val urlSplit = url.split("/")
            val id = urlSplit[urlSplit.lastIndex - 1]

            filmRemoteDataSource.getFilmPlanets(id)
                .retry(3)
                .catch {
                    print(it.toString())
                }
                .collect {
                    planets.add(PlanetMapper.mapToPlanet(it))
                }
        }
        return flow {
            emit(planets)
        }
    }
}