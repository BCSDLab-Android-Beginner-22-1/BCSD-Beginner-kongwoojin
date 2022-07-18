package com.example.myapplication.di

import com.example.myapplication.data.repository.ImageRepositoryImpl
import com.example.myapplication.data.repository.MainRepositoryImpl
import com.example.myapplication.domain.repository.ImageRepository
import com.example.myapplication.domain.repository.MainRepository
import com.example.myapplication.domain.usecase.*
import com.example.myapplication.viewmodel.ImageViewModel
import com.example.myapplication.viewmodel.MainViewModel
import com.example.myapplication.viewmodel.WriteViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { androidContext().contentResolver }

    single<ImageRepository> { ImageRepositoryImpl(get()) }
    single<MainRepository> { MainRepositoryImpl() }

    single { GetArticleListUseCase(get()) }
    single { GetArticleUseCase(get()) }
    single { GetImageListUseCase(get()) }
    single { GetImageUriUseCase(get()) }
    single { LoadImageListUseCase(get()) }
    single { RemoveArticleUseCase(get()) }
    single { UpdateArticleUseCase(get()) }
}

val viewModelModule = module {
    viewModel {
        MainViewModel(get(), get())
    }
    viewModel {
        ImageViewModel(get(), get(), get())
    }
    viewModel {
        WriteViewModel(get(), get())
    }
}