package de.colognecode.superheroes.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import de.colognecode.superheroes.repository.database.entities.SuperHero


object BindingUtils {

    @BindingAdapter("superHeroName")
    @JvmStatic
    fun TextView.setSuperHeroName(item: SuperHero) {
        text = item.name
    }

    @BindingAdapter("superHeroThumbnail")
    @JvmStatic
    fun ImageView.setSuperHeroThumbNail(item: SuperHero) {
        load(item.thumbnail)
    }
}