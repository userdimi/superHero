package de.colognecode.superheroes.data.network

import com.google.gson.JsonObject
import retrofit2.http.GET

interface SuperHeroesService {
    @GET("/v1/public/characters")
    suspend fun fetchHeroes() : JsonObject
}