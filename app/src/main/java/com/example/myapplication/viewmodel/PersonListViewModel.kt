package com.example.myapplication.viewmodel

import android.net.UrlQuerySanitizer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.domain.model.Person
import com.example.myapplication.domain.usecase.GetPersonListUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PersonListViewModel(
    private val getPersonListUseCase: GetPersonListUseCase
) : ViewModel() {
    private val _personList = MutableLiveData<ArrayList<Person>>()
    val personList: LiveData<ArrayList<Person>>
        get() = _personList

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
            getPersonListUseCase.execute(page)
                .collect { people ->
                    _personList.postValue(people.results)
                    if (people.previous != null)
                        _previousPage.postValue(
                            Integer.parseInt(UrlQuerySanitizer(people.previous).getValue("page"))
                        )

                    if (people.next != null)
                        _nextPage.postValue(
                            Integer.parseInt(UrlQuerySanitizer(people.next).getValue("page"))
                        )
                    _page.postValue(page)
                }
            _isLoading.postValue(false)
        }
    }

    fun getInitialData() {
        if (personList.value == null)
            getData(page.value!!)
    }

    fun getPreviousPageData() {
        getData(previousPage.value!!)
    }

    fun getNextPageData() {
        getData(nextPage.value!!)
    }
}