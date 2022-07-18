package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.Article
import com.example.myapplication.domain.repository.MainRepository

class UpdateArticleUseCase(private val mainRepository: MainRepository) {

    fun execute(article: Article, position: Int) {
        mainRepository.updateArticle(article, position)
    }
}