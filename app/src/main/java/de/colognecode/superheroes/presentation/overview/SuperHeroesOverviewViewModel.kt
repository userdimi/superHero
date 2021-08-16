package de.colognecode.superheroes.presentation.overview

import androidx.lifecycle.ViewModel
import de.colognecode.superheroes.presentation.State
import de.colognecode.superheroes.usecases.FetchSuperHeroesUseCase
import kotlinx.coroutines.flow.flow

class SuperHeroesOverviewViewModel(private val fetchSuperHeroesUseCase: FetchSuperHeroesUseCase) : ViewModel() {

    fun getSuperHeroes() = flow {
        emit(State.LoadingState)
        try {
            kotlinx.coroutines.delay(1000)
            emit(State.DataState(fetchSuperHeroesUseCase))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}