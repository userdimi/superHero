package de.colognecode.superheroes

import android.app.Application
import coil.Coil
import coil.ImageLoader
import coil.util.CoilUtils
import coil.util.DebugLogger
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import timber.log.Timber

@HiltAndroidApp
class SuperHeroesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
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
}
