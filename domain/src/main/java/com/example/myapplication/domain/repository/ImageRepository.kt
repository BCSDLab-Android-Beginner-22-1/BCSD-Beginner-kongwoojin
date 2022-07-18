package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.Images

interface ImageRepository {
    fun getImageList(): ArrayList<Images>
    fun getImageUri(position: Int): String
    fun loadImageList()
}