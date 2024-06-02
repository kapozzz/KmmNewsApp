package org.kapozzz.news

import android.app.Application
import data.ktor.networkModule
import org.koin.core.context.startKoin


import presentation.articlesModule

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                networkModule, articlesModule
            )
        }
    }
}