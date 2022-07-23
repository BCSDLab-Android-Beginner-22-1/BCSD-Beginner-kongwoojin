package com.example.myapplication.data.model

import com.google.gson.annotations.SerializedName

data class PersonRootResponse(
    @SerializedName("previous")
    val previous: String?,
    @SerializedName("next")
    val next: String?,
    @SerializedName("results")
    val results: ArrayList<PersonResponse>
)