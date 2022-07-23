package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.Film
import com.example.myapplication.domain.model.Person
import com.example.myapplication.domain.model.Planet
import com.example.myapplication.domain.usecase.GetPersonFilmsUseCase
import com.example.myapplication.domain.usecase.GetPersonHomeWorldUseCase
import com.example.myapplication.domain.usecase.GetPersonUseCase
import kotlinx.coroutines.launch

class PersonViewModel(
    private val getPersonUseCase: GetPersonUseCase,
    private val getPersonHomeWorldUseCase: GetPersonHomeWorldUseCase,
    private val getPersonFilmsUseCase: GetPersonFilmsUseCase
) : ViewModel() {
    private val _person = MutableLiveData<Person>()
    val person: LiveData<Person>
        get() = _person

    private val _homeWorld = MutableLiveData<Planet>()
    val homeWorld: LiveData<Planet>
        get() = _homeWorld

    private val _films = MutableLiveData<ArrayList<Film>>()
    val films: LiveData<ArrayList<Film>>
        get() = _films

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private var url = ""

    fun setUrl(url: String) {
        this.url = url
    }

    fun getData() {
        viewModelScope.launch {
            launch {
                _isLoading.value = true
                getPersonUseCase.execute(url)
                    .collect { person ->
                        _person.value = person
                    }
            }.join()
            launch {
                launch {
                    getPersonHomeWorldUseCase.execute(person.value!!.homeWorld)
                        .collect { homeWorld ->
                            _homeWorld.value = homeWorld
                        }
                }
                launch {
                    getPersonFilmsUseCase.execute(person.value!!.films)
                        .collect { films ->
                            _films.value = films
                        }
                }
            }.join()
            _isLoading.value = false
        }
    }
}