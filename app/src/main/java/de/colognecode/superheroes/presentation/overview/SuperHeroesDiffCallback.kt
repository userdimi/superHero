package de.colognecode.superheroes.presentation.overview

import androidx.recyclerview.widget.DiffUtil
import de.colognecode.superheroes.repository.database.entities.SuperHero

class SuperHeroesDiffCallback : DiffUtil.ItemCallback<SuperHero>() {
    override fun areItemsTheSame(oldItem: SuperHero, newItem: SuperHero): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SuperHero, newItem: SuperHero): Boolean {
        return oldItem == newItem
    }
}
