package com.example.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.Film
import com.example.myapplication.domain.model.Person
import com.example.myapplication.domain.model.Planet
import com.example.myapplication.domain.usecase.GetFilmCharactersUseCase
import com.example.myapplication.domain.usecase.GetFilmPlanetsUseCase
import com.example.myapplication.domain.usecase.GetFilmUseCase
import kotlinx.coroutines.launch

class FilmViewModel(
    private val getFilmUseCase: GetFilmUseCase,
    private val getFilmCharactersUseCase: GetFilmCharactersUseCase,
    private val getFilmPlanetsUseCase: GetFilmPlanetsUseCase
) : ViewModel() {
    private val _film = MutableLiveData<Film>()
    val film: LiveData<Film>
        get() = _film

    private val _characters = MutableLiveData<ArrayList<Person>>()
    val characters: LiveData<ArrayList<Person>>
        get() = _characters

    private val _planets = MutableLiveData<ArrayList<Planet>>()
    val planets: LiveData<ArrayList<Planet>>
        get() = _planets

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
                getFilmUseCase.execute(url)
                    .collect { film ->
                        _film.value = film
                    }
            }.join()
            launch {
                launch {
                    getFilmCharactersUseCase.execute(film.value!!.characters)
                        .collect { characters ->
                            _characters.value = characters
                        }
                }
                launch {
                    getFilmPlanetsUseCase.execute(film.value!!.planets)
                        .collect { planets ->
                            _planets.value = planets
                        }
                }
            }.join()
            _isLoading.value = false
        }
    }
}