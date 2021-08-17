package de.colognecode.superheroes.repository.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.colognecode.superheroes.repository.database.entities.SuperHero

@Dao
interface SuperHeroDao {

    @Query("SELECT * FROM superhero")
    fun getAllSuperHeroes(): LiveData<List<SuperHero>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSuperHero(superHero: SuperHero)
}