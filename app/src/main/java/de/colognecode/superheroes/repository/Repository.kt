package de.colognecode.superheroes.repository

import androidx.lifecycle.LiveData
import de.colognecode.superheroes.repository.database.SuperHeroDao
import de.colognecode.superheroes.repository.database.entities.SuperHero
import de.colognecode.superheroes.repository.network.ApiKeyQuery
import de.colognecode.superheroes.repository.network.SuperHeroesApi
import de.colognecode.superheroes.repository.network.SuperHeroesFetchException
import javax.inject.Inject

class Repository @Inject constructor(
    private val superHeroesApi: SuperHeroesApi,
    private val apiKeyQuery: ApiKeyQuery,
    private val superHeroDao: SuperHeroDao
) {

    val superHeroes: LiveData<List<SuperHero>> = this.superHeroDao.getAllSuperHeroes()

    suspend fun fetchSuperHeroesFromApi() {
        try {
            val superHeroesResponse = this.superHeroesApi.fetchHeroes(
                timeStamp = this.apiKeyQuery.timestamp,
                publicApiKey = this.apiKeyQuery.publicApiKey,
                apiKeyHash = this.apiKeyQuery.apiKeyHash
            )
            if (superHeroesResponse.isSuccessful) {
                superHeroesResponse.body()?.data?.results?.let {
                    for (resultItem in it) {
                        resultItem?.id?.let {
                            val superHero = SuperHero(
                                id = resultItem.id,
                                name = resultItem.name,
                                thumbnail = resultItem.thumbnail?.path,
                            )
                            this.superHeroDao.addSuperHero(superHero)
                        }
                    }
                }
            }
        } catch (exception: Throwable) {
            throw SuperHeroesFetchException(
                "Error by fetching super heroes from the api",
                Throwable()
            )
        }
    }
}
