package com.example.myapplication.domain.model

data class Article(
    var title: String,
    var text: String,
    var writer: String,
    var writeTime: String,
    var imageUri: String?
)
