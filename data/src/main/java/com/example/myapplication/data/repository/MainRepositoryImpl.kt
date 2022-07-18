package com.example.myapplication.data.repository

import com.example.myapplication.data.data.ArticleData.articleData
import com.example.myapplication.data.mapper.ArticleMapper
import com.example.myapplication.domain.model.Article
import com.example.myapplication.domain.repository.MainRepository
import java.text.SimpleDateFormat
import java.util.*

class MainRepositoryImpl : MainRepository {
    override fun updateArticle(article: Article, position: Int) {
        val date = Date()
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.KOREA)
        val currentTime = simpleDateFormat.format(date)

        article.writeTime = currentTime

        if (position == -1) {
            articleData.add(ArticleMapper.mapToArticleResponse(article))
        } else {
            articleData[position] = ArticleMapper.mapToArticleResponse(article)
        }
    }

    override fun getArticleList(): ArrayList<Article> {
        val mappedArticleData = ArrayList<Article>()
        for (article in articleData) {
            mappedArticleData.add(ArticleMapper.mapToArticle(article))
        }
        return mappedArticleData
    }

    override fun getArticle(position: Int): Article {
        return ArticleMapper.mapToArticle(articleData[position])
    }

    override fun removeArticle(position: Int) {
        articleData.removeAt(position)
    }
}