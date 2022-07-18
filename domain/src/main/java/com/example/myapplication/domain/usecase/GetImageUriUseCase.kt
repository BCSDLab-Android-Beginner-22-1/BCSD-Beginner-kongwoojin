package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.repository.ImageRepository

class GetImageUriUseCase(private val imageRepository: ImageRepository) {

    fun execute(position: Int): String {
        return imageRepository.getImageUri(position)
    }
}