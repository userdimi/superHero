package de.colognecode.superheroes.di

import android.app.Application
import androidx.room.Room
import de.colognecode.superheroes.BuildConfig
import de.colognecode.superheroes.presentation.overview.SuperHeroesOverviewViewModel
import de.colognecode.superheroes.repository.Repository
import de.colognecode.superheroes.repository.database.AppDatabase
import de.colognecode.superheroes.repository.database.SuperHeroDao
import de.colognecode.superheroes.repository.network.ApiKeyQuery
import de.colognecode.superheroes.repository.network.SuperHeroesApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@JvmField
val apiModule = module {

    fun provideSuperHeroesApi(retrofit: Retrofit): SuperHeroesApi {
        return retrofit.create(SuperHeroesApi::class.java)
    }

    fun provideApiQuery(): ApiKeyQuery {
        return ApiKeyQuery(
            timestamp = System.currentTimeMillis().toString(),
            privateApiKey = BuildConfig.PRIVATE_API_KEY,
            publicApiKey = BuildConfig.PUBLIC_API_KEY
        )
    }

    single { provideSuperHeroesApi(get()) }
    factory { provideApiQuery() }
}

@JvmField
val networkModule = module {

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

    single { provideRetrofit(get()) }
    single { provideOkHttpClient() }
}

@JvmField
val databaseModule = module {

    fun provideAppDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java, "SuperHeroes-Database"
        ).build()
    }

    fun providesSuperHeroesDao(database: AppDatabase): SuperHeroDao {
        return database.superHeroDao()
    }

    single { provideAppDatabase(androidApplication()) }
    single { providesSuperHeroesDao(get()) }
}

@JvmField
val repositoryModule = module {

    fun provideRepository(
        superHeroesApi: SuperHeroesApi,
        apiKeyQuery: ApiKeyQuery
    ): Repository {
        return Repository(superHeroesApi, apiKeyQuery)
    }

    single { provideRepository(get(), get()) }
}

@JvmField
val viewModelModule = module {
    viewModel { SuperHeroesOverviewViewModel(get()) }
}





