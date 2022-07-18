package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.domain.model.Article
import com.example.myapplication.domain.usecase.UpdateArticleUseCase
import com.example.myapplication.domain.usecase.GetArticleUseCase

class WriteViewModel(
    private val getArticleUseCase: GetArticleUseCase,
    private val updateArticleUseCase: UpdateArticleUseCase
    ) : ViewModel() {
    private val _article = MutableLiveData<Article>()
    val article: LiveData<Article>
        get() = _article

    private var position = -1

    private lateinit var articleData: Article

    fun setArticleData(position: Int) {
        this.position = position
        articleData = if (position == -1) {
            Article("", "", "", "", null)
        } else {
            getArticleUseCase.execute(position)
        }
        _article.value = articleData
    }

    fun setImageUri(newImageUri: String?) {
        articleData.imageUri = newImageUri
        _article.value = articleData
    }

    fun deleteImageUri() {
        articleData.imageUri = null
        _article.value = articleData
    }

    fun addData() {
        updateArticleUseCase.execute(articleData, position)
    }
}