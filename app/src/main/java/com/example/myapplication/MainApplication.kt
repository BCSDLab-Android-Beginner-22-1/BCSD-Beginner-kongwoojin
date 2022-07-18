package com.example.myapplication

import android.app.Application
import com.example.myapplication.di.appModule
import com.example.myapplication.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            androidLogger()
            modules(listOf(appModule, viewModelModule))
        }
    }
}