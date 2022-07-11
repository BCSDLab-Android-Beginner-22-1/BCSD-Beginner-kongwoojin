package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*

class ViewModel : ViewModel() {
    private val _articleList = MutableLiveData<ArrayList<Article>>()
    val articleList: LiveData<ArrayList<Article>>
        get() = _articleList

    private var items = ArrayList<Article>()

    init {
        _articleList.value = items
    }

    fun addData(article: Article, position: Int) {
        val date = Date()
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.KOREA)
        val currentTime = simpleDateFormat.format(date)

        article.writeTime = currentTime

        when (position) {
            -1 -> {
                items.add(article)
                _articleList.value = items
            }
            else -> {
                items[position] = article
                _articleList.value = items
            }
        }
    }

    fun removeData(position: Int) {
        items.removeAt(position)
        _articleList.value = items
    }

    fun getData(position: Int): Article {
        return items[position]
    }
}
