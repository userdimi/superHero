package de.colognecode.superheroes.presentation.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.colognecode.superheroes.repository.Repository
import de.colognecode.superheroes.repository.database.entities.SuperHero
import de.colognecode.superheroes.repository.network.SuperHeroesFetchException
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SuperHeroesOverviewViewModel(private val repository: Repository) : ViewModel() {

    val superHeroes: LiveData<List<SuperHero>> = repository.superHeroes

    val isProgressbarVisible: LiveData<Boolean>
        get() = _isProgressbarVisible

    private var _isProgressbarVisible = MutableLiveData<Boolean>()

    val snackBarText: LiveData<String?>
        get() = _snackBarText

    private var _snackBarText = MutableLiveData<String?>()

    fun getSuperHeroesFromRepository() {
        this.fetchSuperHeroes {
            this.repository.fetchSuperHeroesFromApi()
        }
    }

    private fun fetchSuperHeroes(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                _isProgressbarVisible.value = true
                block()
            } catch (exception: SuperHeroesFetchException) {
                _snackBarText.value = exception.message
            } finally {
                _isProgressbarVisible.value = false
            }
        }
    }
}
