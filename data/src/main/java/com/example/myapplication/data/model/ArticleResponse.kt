package com.example.myapplication.data.model

data class ArticleResponse(
    var title: String,
    var text: String,
    var writer: String,
    var writeTime: String,
    var imageUri: String?
)
