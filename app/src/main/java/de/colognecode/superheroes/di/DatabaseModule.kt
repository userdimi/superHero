package de.colognecode.superheroes.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.colognecode.superheroes.repository.database.AppDatabase
import de.colognecode.superheroes.repository.database.SuperHeroDao

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideAppDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java, "SuperHeroes-Database"
        ).build()
    }

    @Provides
    fun providesSuperHeroesDao(database: AppDatabase): SuperHeroDao {
        return database.superHeroDao()
    }
}
