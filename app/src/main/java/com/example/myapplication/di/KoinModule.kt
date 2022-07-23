package com.example.myapplication.di

import com.example.myapplication.data.repository.FilmRepositoryImpl
import com.example.myapplication.data.repository.PersonRepositoryImpl
import com.example.myapplication.data.repository.PlanetRepositoryImpl
import com.example.myapplication.data.repository.remote.*
import com.example.myapplication.domain.repository.FilmRepository
import com.example.myapplication.domain.repository.PersonRepository
import com.example.myapplication.domain.repository.PlanetRepository
import com.example.myapplication.domain.usecase.*
import com.example.myapplication.viewmodel.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<FilmRepository> { FilmRepositoryImpl(get()) }
    single<PersonRepository> { PersonRepositoryImpl(get()) }
    single<PlanetRepository> { PlanetRepositoryImpl(get()) }

    single<FilmRemoteDataSource> { FilmRemoteDataSourceImpl() }
    single<PersonRemoteDataSource> { PersonRemoteDataSourceImpl() }
    single<PlanetRemoteDataSource> { PlanetRemoteDataSourceImpl() }

    single { GetFilmListUseCase(get()) }
    single { GetPersonListUseCase(get()) }
    single { GetPlanetListUseCase(get()) }
    single { GetFilmUseCase(get()) }
    single { GetPersonUseCase(get()) }
    single { GetPlanetUseCase(get()) }
    single { GetFilmCharactersUseCase(get()) }
    single { GetFilmPlanetsUseCase(get()) }
    single { GetPersonHomeWorldUseCase(get()) }
    single { GetPersonFilmsUseCase(get()) }
    single { GetPlanetFilmsUseCase(get()) }
    single { GetPlanetResidentsUseCase(get()) }

    single { FilmRepositoryImpl(get()) }
    single { PersonRepositoryImpl(get()) }
    single { PlanetRepositoryImpl(get()) }
}

val viewModelModule = module {
    viewModel {
        FilmListViewModel(get())
    }
    viewModel {
        PersonListViewModel(get())
    }
    viewModel {
        PlanetListViewModel(get())
    }
    viewModel {
        FilmViewModel(get(), get(), get())
    }
    viewModel {
        PersonViewModel(get(), get(), get())
    }
    viewModel {
        PlanetViewModel(get(), get(), get())
    }
}