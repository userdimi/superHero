package de.colognecode.superheroes.repository.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.colognecode.superheroes.repository.database.entities.SuperHero

@Dao
interface SuperHeroDao {

    @Query("SELECT * FROM superhero")
    suspend fun getAllSuperHeroes(): List<SuperHero>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSuperHeroes(vararg superHero: SuperHero)
}