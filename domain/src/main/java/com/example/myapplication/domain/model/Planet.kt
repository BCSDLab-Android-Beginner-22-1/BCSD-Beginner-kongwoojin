package com.example.myapplication.domain.model

data class Planet(
    val name: String,
    val rotationPeriod: String,
    val orbitalPeriod: String,
    val climate: String,
    val gravity: String,
    val terrain: String,
    val population: String,
    val residents: ArrayList<String>,
    val films: ArrayList<String>,
    val url: String
)
