package com.example.myapplication.viewmodel

import android.net.UrlQuerySanitizer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.domain.model.Planet
import com.example.myapplication.domain.usecase.GetPlanetListUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlanetListViewModel(
    private val getPlanetListUseCase: GetPlanetListUseCase
) : ViewModel() {
    private val _planetList = MutableLiveData<ArrayList<Planet>>()
    val planetList: LiveData<ArrayList<Planet>>
        get() = _planetList

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
            getPlanetListUseCase.execute(page)
                .collect { planets ->
                    _planetList.postValue(planets.results)
                    if (planets.previous != null)
                        _previousPage.postValue(
                            Integer.parseInt(UrlQuerySanitizer(planets.previous).getValue("page"))
                        )

                    if (planets.next != null)
                        _nextPage.postValue(
                            Integer.parseInt(UrlQuerySanitizer(planets.next).getValue("page"))
                        )

                    _page.postValue(
                        page
                    )
                }
            _isLoading.postValue(false)
        }
    }

    fun getInitialData() {
        if (_planetList.value == null) {
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