package com.example.myapplication.viewmodel

import android.net.UrlQuerySanitizer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.domain.model.Film
import com.example.myapplication.domain.usecase.GetFilmListUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FilmListViewModel(
    private val getFilmListUseCase: GetFilmListUseCase
) : ViewModel() {
    private val _filmList = MutableLiveData<ArrayList<Film>>()
    val filmList: LiveData<ArrayList<Film>>
        get() = _filmList

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _page = MutableLiveData(1)
    val page: LiveData<Int>
        get() = _page

    private val _previousPage = MutableLiveData(1)
    val previousPage: LiveData<Int>
        get() = _previousPage

    private val _nextPage = MutableLiveData(1)
    val nextPage: LiveData<Int>
        get() = _nextPage

    private fun getData(page: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            _isLoading.postValue(true)
            getFilmListUseCase.execute(page)
                .collect { films ->
                    _filmList.postValue(films.results)
                    if (films.previous != null)
                        _previousPage.postValue(
                            Integer.parseInt(UrlQuerySanitizer(films.previous).getValue("page"))
                        )

                    if (films.next != null)
                        _nextPage.postValue(
                            Integer.parseInt(UrlQuerySanitizer(films.next).getValue("page"))
                        )
                    _page.postValue(page)
                }
            _isLoading.postValue(false)
        }
    }

    fun getInitialData() {
        if (_filmList.value == null) {
            getData(page.value!!)
        }
    }

    fun getPreviousPageData() {
        getData(previousPage.value!!)
    }

    fun getNextPageData() {
        getData(nextPage.value!!)
    }
}