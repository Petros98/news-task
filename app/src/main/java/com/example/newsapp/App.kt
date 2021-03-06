package com.example.newsapp

import android.app.Application
import com.example.newsapp.data.articlesModule
import com.example.newsapp.data.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOf(networkModule, articlesModule))
        }
    }
}