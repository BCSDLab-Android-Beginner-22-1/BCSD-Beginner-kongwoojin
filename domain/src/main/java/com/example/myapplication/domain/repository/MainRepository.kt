package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.Article

interface MainRepository {
    fun updateArticle(article: Article, position: Int)
    fun getArticleList(): ArrayList<Article>
    fun getArticle(position: Int): Article
    fun removeArticle(position: Int)
}