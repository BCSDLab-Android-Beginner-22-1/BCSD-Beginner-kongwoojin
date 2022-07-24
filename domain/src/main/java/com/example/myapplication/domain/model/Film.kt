package com.example.myapplication.domain.model

data class Film(
    val title: String,
    val episodeId: Int,
    val openingCrawl: String,
    val director: String,
    val releaseDate: String,
    val characters: ArrayList<String>,
    val planets: ArrayList<String>,
    val url: String
)
