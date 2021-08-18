package de.colognecode.superheroes.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.colognecode.superheroes.repository.database.SuperHeroDao
import de.colognecode.superheroes.repository.database.entities.Comic
import de.colognecode.superheroes.repository.database.entities.SuperHero
import de.colognecode.superheroes.repository.model.SuperHeroesResponse
import de.colognecode.superheroes.repository.network.ApiKeyQuery
import de.colognecode.superheroes.repository.network.SuperHeroesApi
import de.colognecode.superheroes.repository.network.SuperHeroesFetchException
import timber.log.Timber


class Repository(
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
                superHeroesResponse.body()?.data?.results?.let { heroes ->
                    for (hero in heroes) {
                        hero?.id?.let {
                            val superHero = SuperHero(
                                id = hero.id,
                                name = hero.name,
                                thumbnail = hero.thumbnail?.path,
                                description = hero.description,
                                copyright = superHeroesResponse.body()?.copyright ?: ""
                            )
                            this.superHeroDao.addSuperHero(superHero)
                            /*hero.comics?.items?.let { comics ->
                                for (comicItem in comics) {
                                    val comic = Comic(
                                        comicName = comicItem?.name,
                                        resourceURI = comicItem?.resourceURI,
                                        superHero = superHero.name
                                    )
                                    this.superHeroDao.addComic(comic)
                                }
                            }*/
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