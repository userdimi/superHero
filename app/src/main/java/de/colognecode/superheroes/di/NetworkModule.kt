package de.colognecode.superheroes.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.colognecode.superheroes.BuildConfig
import de.colognecode.superheroes.repository.network.ApiKeyQuery
import de.colognecode.superheroes.repository.network.SuperHeroesApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://gateway.marvel.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        okHttpClientBuilder.addInterceptor(loggingInterceptor)
        return okHttpClientBuilder.build()
    }

    @Provides
    fun provideSuperHeroesApi(retrofit: Retrofit): SuperHeroesApi {
        return retrofit.create(SuperHeroesApi::class.java)
    }

    @Provides
    fun provideApiQuery(): ApiKeyQuery {
        return ApiKeyQuery(
            timestamp = System.currentTimeMillis().toString(),
            privateApiKey = BuildConfig.PRIVATE_API_KEY,
            publicApiKey = BuildConfig.PUBLIC_API_KEY
        )
    }
}
