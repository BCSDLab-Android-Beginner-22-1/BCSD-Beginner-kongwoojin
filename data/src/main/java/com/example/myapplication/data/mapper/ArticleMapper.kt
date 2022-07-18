package com.example.myapplication.data.mapper

import com.example.myapplication.data.model.ArticleResponse
import com.example.myapplication.domain.model.Article

object ArticleMapper {
    fun mapToArticle(articleResponse: ArticleResponse): Article {
        return Article(
            title = articleResponse.title,
            text = articleResponse.text,
            writer = articleResponse.writer,
            writeTime = articleResponse.writeTime,
            imageUri = articleResponse.imageUri
        )
    }

    fun mapToArticleResponse(article: Article): ArticleResponse {
        return ArticleResponse(
            title = article.title,
            text = article.text,
            writer = article.writer,
            writeTime = article.writeTime,
            imageUri = article.imageUri
        )
    }
}