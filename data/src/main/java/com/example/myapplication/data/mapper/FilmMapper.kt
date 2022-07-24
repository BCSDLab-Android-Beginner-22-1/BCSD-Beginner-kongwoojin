package com.example.myapplication.data.mapper

import com.example.myapplication.data.model.FilmResponse
import com.example.myapplication.data.model.FilmRootResponse
import com.example.myapplication.domain.model.Film
import com.example.myapplication.domain.model.FilmRoot

object FilmMapper {
    fun mapToFilmList(list: FilmRootResponse): FilmRoot {
        val mappedList = ArrayList<Film>()

        for (film in list.results) {
            mappedList.add(
                Film(
                    title = film.title,
                    episodeId = film.episodeId,
                    openingCrawl = film.openingCrawl,
                    director = film.director,
                    releaseDate = film.releaseDate,
                    characters = film.characters,
                    planets = film.planets,
                    url = film.url
                )
            )
        }

        return FilmRoot(
            previous = list.previous,
            next = list.next,
            results = mappedList
        )
    }

    fun mapToFilms(filmResponse: FilmResponse): Film {
        return Film(
            title = filmResponse.title,
            episodeId = filmResponse.episodeId,
            openingCrawl = filmResponse.openingCrawl,
            director = filmResponse.director,
            releaseDate = filmResponse.releaseDate,
            characters = filmResponse.characters,
            planets = filmResponse.planets,
            url = filmResponse.url
        )
    }
}