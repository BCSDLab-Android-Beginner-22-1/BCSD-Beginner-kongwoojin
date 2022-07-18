package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.repository.MainRepository

class RemoveArticleUseCase(private val mainRepository: MainRepository) {

    fun execute(position: Int){
        mainRepository.removeArticle(position)
    }
}