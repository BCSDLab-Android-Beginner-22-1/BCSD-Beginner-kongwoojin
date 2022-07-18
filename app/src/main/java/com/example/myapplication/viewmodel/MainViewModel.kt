package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.domain.model.Article
import com.example.myapplication.domain.usecase.GetArticleListUseCase
import com.example.myapplication.domain.usecase.RemoveArticleUseCase

class MainViewModel(
    private val getArticleListUseCase: GetArticleListUseCase,
    private val removeArticleUseCase: RemoveArticleUseCase
) : ViewModel() {
    private val _articleList = MutableLiveData<ArrayList<Article>>()
    val articleList: LiveData<ArrayList<Article>>
        get() = _articleList

    init {
        _articleList.value = getArticleListUseCase.execute()
    }

    fun removeData(position: Int) {
        removeArticleUseCase.execute(position)
        _articleList.value = getArticleListUseCase.execute()
    }

    fun reloadData() {
        _articleList.value = getArticleListUseCase.execute()
    }
}
