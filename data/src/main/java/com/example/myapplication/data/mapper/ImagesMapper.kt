package com.example.myapplication.data.mapper

import com.example.myapplication.data.model.ImagesResponse
import com.example.myapplication.domain.model.Images

object ImagesMapper {
    fun mapToImages(imagesResponse: ImagesResponse): Images {
        return Images(imagesResponse.uri)
    }
}