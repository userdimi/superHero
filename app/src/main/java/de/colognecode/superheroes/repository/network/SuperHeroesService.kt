package de.colognecode.superheroes.repository.network

import de.colognecode.superheroes.repository.model.SuperHeroesResponse
import retrofit2.http.GET

interface SuperHeroesService {
    @GET("/v1/public/characters")
    suspend fun fetchHeroes() : SuperHeroesResponse
}