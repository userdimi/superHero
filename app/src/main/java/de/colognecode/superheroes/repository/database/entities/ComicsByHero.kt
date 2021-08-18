package de.colognecode.superheroes.repository.database.entities

import androidx.room.Embedded
import androidx.room.Relation


data class ComicsByHero(
    @Embedded
    val superHero: SuperHero,
    @Relation(
        parentColumn = "id",
        entityColumn = "superHero"
    )
    val comics: List<Comic>
)