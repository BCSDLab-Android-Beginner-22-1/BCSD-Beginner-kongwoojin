package com.example.myapplication.data.model

import com.google.gson.annotations.SerializedName

data class FilmResponse(
    @SerializedName("title")
    val title: String,
    @SerializedName("episode_id")
    val episodeId: Int,
    @SerializedName("opening_crawl")
    val openingCrawl: String,
    @SerializedName("director")
    val director: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("characters")
    val characters: ArrayList<String>,
    @SerializedName("planets")
    val planets: ArrayList<String>,
    @SerializedName("url")
    val url: String
)
