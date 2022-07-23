package com.example.myapplication.data.model

import com.google.gson.annotations.SerializedName

data class PlanetRootResponse(
    @SerializedName("previous")
    val previous: String?,
    @SerializedName("next")
    val next: String?,
    @SerializedName("results")
    val results: ArrayList<PlanetResponse>
)