package com.example.myapplication.data.model

import com.google.gson.annotations.SerializedName

data class PersonResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("height")
    val height: String,
    @SerializedName("mass")
    val mass: String,
    @SerializedName("birth_year")
    val birthYear: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("homeworld")
    val homeWorld: String,
    @SerializedName("films")
    val films: ArrayList<String>,
    @SerializedName("url")
    val url: String
)
