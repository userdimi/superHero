package de.colognecode.superheroes.usecases

import com.google.gson.JsonObject
import de.colognecode.superheroes.data.network.SuperHeroesService


class FetchSuperHeroesUseCase(private val superHeroesService: SuperHeroesService) {

    suspend operator fun invoke(): JsonObject {
        return superHeroesService.fetchHeroes()
    }
}