package com.example.myapplication.data.model

import com.google.gson.annotations.SerializedName

data class PlanetResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("rotation_period")
    val rotationPeriod: String,
    @SerializedName("orbital_period")
    val orbitalPeriod: String,
    @SerializedName("climate")
    val climate: String,
    @SerializedName("gravity")
    val gravity: String,
    @SerializedName("terrain")
    val terrain: String,
    @SerializedName("population")
    val population: String,
    @SerializedName("residents")
    val residents: ArrayList<String>,
    @SerializedName("films")
    val films: ArrayList<String>,
    @SerializedName("url")
    val url: String
)
