package com.example.myapplication.data.api

import com.example.myapplication.data.model.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface API {
    @GET("films/")
    suspend fun getFilmList(
        @Query("page") page: Int
    ): FilmRootResponse

    @GET("people/")
    suspend fun getPersonList(
        @Query("page") page: Int
    ): PersonRootResponse

    @GET("planets/")
    suspend fun getPlanetList(
        @Query("page") page: Int
    ): PlanetRootResponse

    @GET("films/{id}")
    suspend fun getFilm(
        @Path("id") id: String
    ): FilmResponse

    @GET("people/{id}")
    suspend fun getPerson(
        @Path("id") id: String
    ): PersonResponse

    @GET("planets/{id}")
    suspend fun getPlanet(
        @Path("id") id: String
    ): PlanetResponse
}
