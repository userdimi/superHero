package de.colognecode.superheroes.di

import de.colognecode.superheroes.presentation.overview.SuperHeroesOverviewViewModel
import de.colognecode.superheroes.repository.Repository
import de.colognecode.superheroes.repository.network.SuperHeroesService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigInteger
import java.security.MessageDigest

@JvmField
val appModule = module {

    viewModel { SuperHeroesOverviewViewModel(get()) }
    single { provideRetrofit(get()) }
    factory { provideOkHttpClient() }
    factory { provideSuperHeroesService(get()) }
    factory { provideRepository(get()) }
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
): Repository {
    return Repository(superHeroesService, getTimeStamp(), getPublicApiKey(), getApiKeyHash())
}

fun getTimeStamp(): String {
    return System.currentTimeMillis().toString()
}

fun getPublicApiKey(): String {
    return "ef42e27b12df2e212b7372cea579ed49"
}

fun getPrivateApiKey(): String {
    return "de4da4ee60348b4c563db10ca9937b391fddeb07"
}

fun getApiKeyHash(): String {
    val md = MessageDigest.getInstance("MD5")
    val apiKeyToHash = "${getTimeStamp()}${getPrivateApiKey()}${getPublicApiKey()}"
    return BigInteger(1, md.digest(apiKeyToHash.toByteArray())).toString(16).padStart(32, '0')
}
