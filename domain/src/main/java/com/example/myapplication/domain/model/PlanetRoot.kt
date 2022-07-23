package com.example.myapplication.domain.model

data class PlanetRoot(
    val previous: String?,
    val next: String?,
    val results: ArrayList<Planet>
)