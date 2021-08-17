package de.colognecode.superheroes.presentation.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.colognecode.superheroes.presentation.State
import de.colognecode.superheroes.repository.Repository
import de.colognecode.superheroes.repository.model.SuperHeroesResponse
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class SuperHeroesOverviewViewModel(private val repository: Repository) : ViewModel() {

    var superHeroes: LiveData<SuperHeroesResponse?>? = null

    fun fetchSuperHeroes() {
        // TODO: 17.08.21 get superheroes from repository
        viewModelScope.launch {
            superHeroes = repository.fetchSuperHeroesFromApi()
        }
    }

}