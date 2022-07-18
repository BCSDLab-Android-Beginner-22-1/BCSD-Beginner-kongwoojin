package com.example.myapplication.data.repository

import android.content.ContentResolver
import android.provider.MediaStore
import com.example.myapplication.data.data.ImageData.imageData
import com.example.myapplication.data.mapper.ImagesMapper
import com.example.myapplication.data.model.ImagesResponse
import com.example.myapplication.domain.model.Images
import com.example.myapplication.domain.repository.ImageRepository

class ImageRepositoryImpl(
    private val contentResolver: ContentResolver
) : ImageRepository {
    override fun loadImageList() {
        val imageList = ArrayList<ImagesResponse>()

        val projection = arrayOf(MediaStore.Images.Media._ID)

        val cursor = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            null
        )

        cursor?.use {
            val idColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)

                val albumUri = "${MediaStore.Images.Media.EXTERNAL_CONTENT_URI}/$id"
                imageList.add(ImagesResponse(albumUri))
            }
        }
        imageData = imageList
    }

    override fun getImageList(): ArrayList<Images> {
        val mappedImageData = java.util.ArrayList<Images>()
        for (image in imageData) {
            mappedImageData.add(ImagesMapper.mapToImages(image))
        }
        return mappedImageData
    }

    override fun getImageUri(position: Int): String {
        return imageData[position].uri
    }
}