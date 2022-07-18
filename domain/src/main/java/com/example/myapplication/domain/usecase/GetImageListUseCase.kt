package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.Images
import com.example.myapplication.domain.repository.ImageRepository

class GetImageListUseCase(private val imageRepository: ImageRepository) {

    fun execute(): ArrayList<Images> {
        return imageRepository.getImageList()
    }
}