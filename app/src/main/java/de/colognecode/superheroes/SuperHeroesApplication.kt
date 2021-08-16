package de.colognecode.superheroes

import android.app.Application
import de.colognecode.superheroes.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class SuperHeroesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        this.startKoin()
        this.initTimber()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun startKoin() {
        startKoin {
            androidLogger()
            androidContext(this@SuperHeroesApplication)
            modules(appModule)
        }
    }

}