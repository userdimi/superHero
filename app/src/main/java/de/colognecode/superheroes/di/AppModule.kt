package de.colognecode.superheroes.di

import de.colognecode.superheroes.overview.SuperHeroesOverviewViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@JvmField
val appModule = module {

    viewModel { SuperHeroesOverviewViewModel() }

}