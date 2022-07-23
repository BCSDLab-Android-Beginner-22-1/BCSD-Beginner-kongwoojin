package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.Film
import com.example.myapplication.domain.model.Person
import com.example.myapplication.domain.model.Planet
import com.example.myapplication.domain.usecase.GetPlanetFilmsUseCase
import com.example.myapplication.domain.usecase.GetPlanetResidentsUseCase
import com.example.myapplication.domain.usecase.GetPlanetUseCase
import kotlinx.coroutines.launch

class PlanetViewModel(
    private val getPlanetUseCase: GetPlanetUseCase,
    private val getPlanetResidentsUseCase: GetPlanetResidentsUseCase,
    private val getPlanetFilmsUseCase: GetPlanetFilmsUseCase
) : ViewModel() {
    private val _planet = MutableLiveData<Planet>()
    val planet: LiveData<Planet>
        get() = _planet

    private val _residents = MutableLiveData<ArrayList<Person>>()
    val residents: LiveData<ArrayList<Person>>
        get() = _residents

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
                getPlanetUseCase.execute(url)
                    .collect { planet ->
                        _planet.value = planet
                    }
            }.join()
            launch {
                launch {
                    getPlanetResidentsUseCase.execute(planet.value!!.residents)
                        .collect { residents ->
                            _residents.value = residents
                        }
                }
                launch {
                    getPlanetFilmsUseCase.execute(planet.value!!.films)
                        .collect { films ->
                            _films.value = films
                        }
                }
            }.join()
            _isLoading.value = false
        }
    }
}