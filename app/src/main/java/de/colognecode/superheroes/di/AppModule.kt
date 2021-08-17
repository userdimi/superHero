package de.colognecode.superheroes.di

import de.colognecode.superheroes.BuildConfig
import de.colognecode.superheroes.presentation.overview.SuperHeroesOverviewViewModel
import de.colognecode.superheroes.repository.Repository
import de.colognecode.superheroes.repository.network.ApiKeyQuery
import de.colognecode.superheroes.repository.network.SuperHeroesService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.math.BigInteger
import java.security.MessageDigest

@JvmField
val appModule = module {

    viewModel { SuperHeroesOverviewViewModel(get()) }
    single { provideRetrofit(get()) }
    factory { provideOkHttpClient() }
    factory { provideSuperHeroesService(get()) }
    factory { provideApiQuery() }
    factory { provideRepository(get(), get()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://gateway.marvel.com/")
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

fun provideSuperHeroesService(retrofit: Retrofit): SuperHeroesService {
    return retrofit.create(SuperHeroesService::class.java)
}

fun provideRepository(
    superHeroesService: SuperHeroesService,
    apiKeyQuery: ApiKeyQuery
): Repository {
    return Repository(superHeroesService, apiKeyQuery)
}

fun provideApiQuery(): ApiKeyQuery {
    return ApiKeyQuery(
        timestamp = System.currentTimeMillis().toString(),
        privateApiKey = BuildConfig.PRIVATE_API_KEY,
        publicApiKey = BuildConfig.PUBLIC_API_KEY
    )
}
