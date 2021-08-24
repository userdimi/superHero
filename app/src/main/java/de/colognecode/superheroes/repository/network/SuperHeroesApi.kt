package de.colognecode.superheroes.repository.network

import de.colognecode.superheroes.repository.model.SuperHeroesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SuperHeroesApi {

    @GET("/v1/public/characters")
    suspend fun fetchHeroes(
        @Query("ts") timeStamp: String,
        @Query("apikey") publicApiKey: String,
        @Query("hash") apiKeyHash: String
    ): Response<SuperHeroesResponse>
}
