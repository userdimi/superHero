package de.colognecode.superheroes

import android.app.Application
import coil.Coil
import coil.ImageLoader
import coil.util.CoilUtils
import coil.util.DebugLogger
import de.colognecode.superheroes.di.*
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class SuperHeroesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        this.startKoin()
        this.initTimber()
        val imageLoader = ImageLoader.Builder(this)
            .logger(DebugLogger())
            .okHttpClient {
                OkHttpClient.Builder()
                    .cache(CoilUtils.createDefaultCache(this))
                    .build()
            }
            .build()
        Coil.setImageLoader(imageLoader)
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
            modules(
                apiModule,
                networkModule,
                databaseModule,
                repositoryModule,
                viewModelModule
            )
        }
    }
}
