package de.colognecode.superheroes.presentation.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import de.colognecode.superheroes.databinding.ItemSuperHeroBinding
import de.colognecode.superheroes.presentation.overview.SuperHeroesAdapter.ViewHolder
import de.colognecode.superheroes.repository.database.entities.SuperHero


class SuperHeroesAdapter(private val superHeroes: List<SuperHero>?) :
    RecyclerView.Adapter<ViewHolder>() {

    inner class ViewHolder(private val binding: ItemSuperHeroBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(superHeroItem: SuperHero?) {
            with(binding) {
                imageViewSuperHeroThumbnail.load(superHeroItem?.thumbnail)
                textViewSuperHeroName.text = superHeroItem?.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSuperHeroBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(superHeroes?.get(position))

    override fun getItemCount(): Int = this.superHeroes?.size ?: 0

}