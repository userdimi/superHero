package de.colognecode.superheroes.repository

import androidx.lifecycle.MutableLiveData
import de.colognecode.superheroes.repository.model.SuperHeroesResponse
import de.colognecode.superheroes.repository.network.ApiKeyQuery
import de.colognecode.superheroes.repository.network.SuperHeroesApi
import timber.log.Timber


class Repository(
    private val superHeroesApi: SuperHeroesApi,
    private val apiKeyQuery: ApiKeyQuery
) {

    val superHeroes: MutableLiveData<SuperHeroesResponse?>? = null

    suspend fun fetchSuperHeroesFromApi(): MutableLiveData<SuperHeroesResponse?>? {
        try {
            val superHeroesResponse = this.superHeroesApi.fetchHeroes(
                timeStamp = this.apiKeyQuery.timestamp,
                publicApiKey = this.apiKeyQuery.publicApiKey,
                apiKeyHash = this.apiKeyQuery.apiKeyHash
            )
            if (superHeroesResponse.isSuccessful) {
                superHeroes?.postValue(superHeroesResponse.body())
            }
        } catch (exception: Throwable) {
            Timber.e("Error by fetching super heroes from the api: $exception")
        }
        return this.superHeroes
    }
}