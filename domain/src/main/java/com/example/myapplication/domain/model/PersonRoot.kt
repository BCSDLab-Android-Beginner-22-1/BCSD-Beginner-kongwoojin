package com.example.myapplication.domain.model

data class PersonRoot(
    val previous: String?,
    val next: String?,
    val results: ArrayList<Person>
)