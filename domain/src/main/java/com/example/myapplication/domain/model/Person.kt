package com.example.myapplication.domain.model

data class Person(
    val name: String,
    val height: String,
    val mass: String,
    val birthYear: String,
    val gender: String,
    val homeWorld: String,
    val films: ArrayList<String>,
    val url: String
)
