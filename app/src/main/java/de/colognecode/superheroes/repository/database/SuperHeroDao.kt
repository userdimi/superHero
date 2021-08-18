package de.colognecode.superheroes.repository.database

import androidx.lifecycle.LiveData
import androidx.room.*
import de.colognecode.superheroes.repository.database.entities.Comic
import de.colognecode.superheroes.repository.database.entities.ComicsByHero
import de.colognecode.superheroes.repository.database.entities.SuperHero

@Dao
interface SuperHeroDao {

    @Query("SELECT * FROM superhero")
    fun getAllSuperHeroes(): LiveData<List<SuperHero>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSuperHero(superHero: SuperHero)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addComic(comic: Comic)

    @Transaction
    @Query("SELECT * FROM superhero where id=:id")
    suspend fun getComicsByHero(id: String): ComicsByHero
}