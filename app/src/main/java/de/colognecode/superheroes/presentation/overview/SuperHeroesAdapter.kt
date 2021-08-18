package de.colognecode.superheroes.presentation.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.colognecode.superheroes.R
import de.colognecode.superheroes.databinding.ItemSuperHeroBinding
import de.colognecode.superheroes.repository.database.entities.SuperHero


class SuperHeroesAdapter :
    ListAdapter<SuperHero, SuperHeroesAdapter.ViewHolder>(SuperHeroesDiffCallback()) {

    class ViewHolder private constructor(private val binding: ItemSuperHeroBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SuperHero) {
            this.binding.superHero = item
            this.binding.executePendingBindings()
            this.itemView.setOnClickListener {
                it.findNavController()
                    .navigate(R.id.action_superHeroesOverviewFragment_to_superHeroDetailFragment)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemSuperHeroBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}