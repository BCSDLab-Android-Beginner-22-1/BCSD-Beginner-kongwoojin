package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.Article
import com.example.myapplication.domain.repository.MainRepository

class GetArticleUseCase(private val mainRepository: MainRepository) {

    fun execute(position: Int): Article {
        return mainRepository.getArticle(position)
    }
}