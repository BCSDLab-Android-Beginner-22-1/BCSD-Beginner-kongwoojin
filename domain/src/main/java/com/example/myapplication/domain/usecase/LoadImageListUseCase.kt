package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.repository.ImageRepository

class LoadImageListUseCase(private val imageRepository: ImageRepository) {

    fun execute() {
        return imageRepository.loadImageList()
    }
}