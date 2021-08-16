package de.colognecode.superheroes

import android.app.Application
import de.colognecode.superheroes.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SuperHeroesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin {
            androidLogger()
            androidContext(this@SuperHeroesApplication)
            modules(appModule)
        }
    }
}