package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.Article
import com.example.myapplication.domain.repository.MainRepository

class GetArticleListUseCase(private val mainRepository: MainRepository) {

    fun execute(): ArrayList<Article> {
        return mainRepository.getArticleList()
    }
}