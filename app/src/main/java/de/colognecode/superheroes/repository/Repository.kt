package de.colognecode.superheroes.repository

import androidx.lifecycle.MutableLiveData
import de.colognecode.superheroes.repository.model.SuperHeroesResponse
import de.colognecode.superheroes.repository.network.ApiKeyQuery
import de.colognecode.superheroes.repository.network.SuperHeroesService
import timber.log.Timber


class Repository(
    private val superHeroesService: SuperHeroesService,
    private val apiKeyQuery: ApiKeyQuery
) {

    val superHeroes: MutableLiveData<SuperHeroesResponse?>? = null

    suspend fun fetchSuperHeroesFromApi(): MutableLiveData<SuperHeroesResponse?>? {
        try {
            val superHeroesResponse = this.superHeroesService.fetchHeroes(
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