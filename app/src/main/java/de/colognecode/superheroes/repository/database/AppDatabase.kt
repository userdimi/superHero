package de.colognecode.superheroes.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import de.colognecode.superheroes.repository.database.entities.Comic
import de.colognecode.superheroes.repository.database.entities.SuperHero

@Database(entities = [SuperHero::class, Comic::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun superHeroDao(): SuperHeroDao
}