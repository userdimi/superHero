package de.colognecode.superheroes.di

import de.colognecode.superheroes.overview.SuperHeroesOverviewViewModel
import de.colognecode.superheroes.repository.Repository
import de.colognecode.superheroes.repository.network.SuperHeroesService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@JvmField
val appModule = module {

    viewModel { SuperHeroesOverviewViewModel() }
    single { provideRetrofit(get()) }
    factory { provideOkHttpClient() }
    factory { providesSuperHeroesService(get()) }
    factory { Repository(get()) }

}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl("")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideOkHttpClient(): OkHttpClient {
    val okHttpClientBuilder = OkHttpClient.Builder()
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    okHttpClientBuilder.addInterceptor(loggingInterceptor)
    return okHttpClientBuilder.build()
}

fun providesSuperHeroesService(retrofit: Retrofit): SuperHeroesService {
    return retrofit.create(SuperHeroesService::class.java)
}